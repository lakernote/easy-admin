
package com.laker.admin.framework.ext.mybatis.datapermission;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * 数据权限处理器 TODO
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings({"rawtypes"})
@Slf4j
public class LakerDataPermissionV2Interceptor extends JsqlParserSupport implements InnerInterceptor {
    private LakerV2DataPermissionHandler dataPermissionHandler = new LakerV2DataPermissionHandler();

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) return;
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
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
//        if (SqlCommandType.UPDATE != ms.getSqlCommandType()) {
//            return;
//        }
//        if (parameter instanceof Map) {
//            Map<String, Object> map = (Map<String, Object>) parameter;
//        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        parserSingle(mpBs.sql(), ms.getId());

    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        Expression where = update.getWhere();
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
        log.debug(delete.toString());
    }
}
