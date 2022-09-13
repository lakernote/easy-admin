package com.laker.admin.framework.ext.mybatis.datapermission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.ext.mybatis.UserDataPower;
import com.laker.admin.framework.ext.mybatis.UserInfoAndPowers;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 这种只能处理查询 不能处理 cud
 * 且不支持别名
 */
@Slf4j
public class LakerV2DataPermissionHandler {

    public static final String WHERE = " where {}";

    /**
     * @param plainSelect       plainSelect
     * @param mappedStatementId Mapper接口方法ID
     * @return
     */
    @SneakyThrows
    public Expression getSqlSegment(PlainSelect plainSelect, String mappedStatementId) {
        // 获取原SQL Where 条件表达式
        Expression where = plainSelect.getWhere();
        // 获取sql语句的from 主表
        FromItem fromItem = plainSelect.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        String mainTableName = "";
        // 如果是 table 类型
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            // 有别名用别名，无别名用表名，防止字段冲突报错
            Alias fromItemAlias = fromItem.getAlias();
            mainTableName = fromItemAlias == null ? fromTable.getName() : fromItemAlias.getName();
        } else if (fromItem instanceof SubSelect) {
            SubSelect fromTable = (SubSelect) fromItem;
            // 有别名用别名，无别名用表名，防止字段冲突报错
            Alias fromItemAlias = fromItem.getAlias();
            mainTableName = fromItemAlias == null ? fromTable.getAlias().getName() : fromItemAlias.getName();
        } else {
            Alias fromItemAlias = fromItem.getAlias();
            mainTableName = fromItemAlias.getName();
        }

        List<String> split = StrUtil.split(mappedStatementId, '.');
        int index = split.size();
        String method = split.get(index - 1);
        String mapper = split.get(index - 2);
        UserInfoAndPowers userInfoAndPowers = EasyAdminSecurityUtils.getCurrentUserInfo();
        if (userInfoAndPowers == null) {
            return where;
        }
        if (userInfoAndPowers.isSuperAdmin()) {
            return where;
        }
        List<UserDataPower> userDataPowers = userInfoAndPowers.getUserDataPowers();
        if (CollUtil.isEmpty(userDataPowers)) {
            return where;
        }
        String powerCode = mapper + "." + method;
        Optional<UserDataPower> dataPower = userDataPowers.stream()
                .filter(sysDataPower -> StrUtil.equalsIgnoreCase(powerCode, sysDataPower.getPowerCode()))
                .findFirst();
        if (!dataPower.isPresent()) {
            return where;
        }
        try {
            // 1. 获取权限过滤相关信息
            log.info("进行权限过滤,dataPowerFilterType:{} , where: {},mappedStatementId: {}", dataPower.get().getDataFilterType(), where, mappedStatementId);
            Expression expression = new HexValue(" 1 = 1 ");
            if (where == null) {
                where = expression;
            }

            switch (dataPower.get().getDataFilterType()) {
                // 查看全部
                case ALL:
                    return where;
                // 查看本人所在组织机构以及下属机构
                case DEPT_SETS:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    Set<Long> deptIds = userInfoAndPowers.getDeptIds();
                    // 把集合转变为JSQLParser需要的元素列表
                    ItemsList itemsList = new ExpressionList(deptIds.stream().map(LongValue::new).collect(Collectors.toList()));
                    InExpression inExpression = new InExpression(new Column(mainTableName + ".create_dept_id"), itemsList);
                    AndExpression andExpression = new AndExpression(where, inExpression);
                    log.info(WHERE, andExpression);
                    return andExpression;
                // 查看当前部门的数据
                case DEPT:
                    //  = 表达式
                    // dept_id = deptId
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column(mainTableName + ".create_dept_id"));
                    equalsTo.setRightExpression(new LongValue(userInfoAndPowers.getDeptId()));
                    // 创建 AND 表达式 拼接Where 和 = 表达式
                    // WHERE xxx AND dept_id = 3
                    AndExpression deptAndExpression = new AndExpression(where, equalsTo);
                    log.debug(WHERE, deptAndExpression);
                    return deptAndExpression;
                // 查看自己的数据
                case SELF:
                    // create_by = userId
                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(mainTableName + ".create_by"));
                    selfEqualsTo.setRightExpression(new LongValue(userInfoAndPowers.getUserId()));
                    AndExpression selfAndExpression = new AndExpression(where, selfEqualsTo);
                    log.info(WHERE, selfAndExpression);
                    return selfAndExpression;
                case DIY:
                    return new AndExpression(where, new StringValue(userInfoAndPowers.getSql()));
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("LakerDataPermissionHandler.err", e);
        }
        return where;
    }
}
