
package com.laker.admin.framework.ext.mybatis.datapermission;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;

/**
 * 自定义数据权限处理器
 *
 * @see DataPermissionInterceptor
 * @see TenantLineInnerInterceptor
 */
@Slf4j
public class EasyDataPermissionV2Interceptor extends JsqlParserSupport implements InnerInterceptor {
    private final EasyV2DataPermissionHandler dataPermissionHandler = new EasyV2DataPermissionHandler();

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
                return;
            }
            if (SqlParserHelper.getSqlParserInfo(ms)) {
                return;
            }
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(parserMulti(mpBs.sql(), null));
        }
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Expression sqlSegment = dataPermissionHandler.getSqlSegment(plainSelect, (String) obj);
        if (null != sqlSegment) {
            plainSelect.setWhere(sqlSegment);
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        // update 语句处理
        final Table table = update.getTable();
        if (!dataPermissionHandler.support(table.getName())) {
            // 过滤退出执行
            return;
        }
        update.setWhere(this.andExpression(table, update.getWhere()));
    }

    /**
     * 新增
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        log.debug(insert.toString());
    }

    /**
     * 删除
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        // 删除语句处理
        final Table table = delete.getTable();
        if (!dataPermissionHandler.support(table.getName())) {
            // 过滤退出执行
            return;
        }
        delete.setWhere(this.andExpression(table, delete.getWhere()));
        log.info("权限插件处理后delete:{}", delete);
    }

    private BinaryExpression andExpression(Table table, Expression where) {
        //获得where条件表达式
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        if (StpUtil.hasRole("admin")) {
            // 管理员 处理的是部门 获取当前登录人部门
            equalsTo.setRightExpression(new LongValue(EasyAdminSecurityUtils.getCurrentUserInfo().getDeptId()));
        } else {
            // 获取当前登录人ID
            equalsTo.setRightExpression(new LongValue(StpUtil.getLoginIdAsLong()));
        }

        if (null != where) {
            if (where instanceof OrExpression) {
                return new AndExpression(equalsTo, new Parenthesis(where));
            } else {
                return new AndExpression(equalsTo, where);
            }
        }
        return equalsTo;
    }

    /**
     * 用户字段别名设置
     * <p>create_by 或 tableAlias.create_by</p>
     *
     * @param table 表对象
     * @return 字段
     */
    private Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(StringPool.DOT);
        }
        // 管理员 处理本部门
        if (StpUtil.hasRole("admin")) {
            column.append("create_dept_id");
            return new Column(column.toString());
        }
        // 非管理员 处理个人
        column.append("create_by");
        return new Column(column.toString());
    }

}
