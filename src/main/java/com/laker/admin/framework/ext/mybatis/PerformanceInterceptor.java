/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.laker.admin.framework.ext.mybatis;

import com.baomidou.mybatisplus.core.toolkit.*;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;

/**
 * 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
 *
 * @author hubin nieqiurong TaoYu
 * @since 2016-07-07
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@Slf4j
public class PerformanceInterceptor implements Interceptor {

    private final static SqlFormatter SQL_FORMATTER = new SqlFormatter();
    private static final String DruidPooledPreparedStatement = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4CPreparedStatement = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String OraclePreparedStatementWrapper = "oracle.jdbc.driver.OraclePreparedStatementWrapper";
    /**
     * SQL 执行最大时长，超过自动停止运行，有助于发现问题。
     */
    @Setter
    @Getter
    @Accessors(chain = true)
    private long maxTime = 0;
    /**
     * SQL 是否格式化
     */
    @Setter
    @Getter
    @Accessors(chain = true)
    private boolean format = false;
    /**
     * 是否写入日志文件
     * <p>true 写入日志文件，不阻断程序执行！</p>
     * <p>超过设定的最大执行时长异常提示！</p>
     */
    @Setter
    @Getter
    @Accessors(chain = true)
    private boolean writeInLog = false;
    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSQLMethod;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement statement;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception e) {
            // do nothing
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            //Hikari
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception ignored) {

            }
        }

        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        if (DruidPooledPreparedStatement.equals(stmtClassName)) {
            try {
                if (druidGetSQLMethod == null) {
                    Class<?> clazz = Class.forName(DruidPooledPreparedStatement);
                    druidGetSQLMethod = clazz.getMethod("getSql");
                }
                Object stmtSql = druidGetSQLMethod.invoke(statement);
                if (stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (T4CPreparedStatement.equals(stmtClassName)
                || OraclePreparedStatementWrapper.equals(stmtClassName)) {
            try {
                if (oracleGetOriginalSqlMethod != null) {
                    Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql instanceof String) {
                        originalSql = (String) stmtSql;
                    }
                } else {
                    Class<?> clazz = Class.forName(stmtClassName);
                    oracleGetOriginalSqlMethod = getMethodRegular(clazz, "getOriginalSql");
                    if (oracleGetOriginalSqlMethod != null) {
                        //OraclePreparedStatementWrapper is not a public class, need set this.
                        oracleGetOriginalSqlMethod.setAccessible(true);
                        if (null != oracleGetOriginalSqlMethod) {
                            Object stmtSql = oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql instanceof String) {
                                originalSql = (String) stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //ignore
            }
        }
        if (originalSql == null) {
            originalSql = statement.toString();
        }
        originalSql = originalSql.replaceAll("[\\s]+", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 计算执行 SQL 耗时
        long start = SystemClock.now();
        String mapperId = ms.getId();
        TraceContext.addSpan(mapperId, SpanType.Mapper);
        Object result;
        try {
            result = invocation.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            TraceContext.stopSpan(this.getMaxTime() >= 1 ? this.getMaxTime() : 1000);
            long timing = SystemClock.now() - start;

            // 格式化 SQL 打印执行结果
            StringBuilder formatSql = new StringBuilder()
                    .append(" Time：").append(timing)
                    .append(" ms - ID：").append(mapperId)
                    .append(StringPool.NEWLINE).append("Execute SQL：")
                    .append(SQL_FORMATTER.format(originalSql)).append(StringPool.NEWLINE);

            if (this.isWriteInLog()) {
                if (this.getMaxTime() >= 1 && timing > this.getMaxTime()) {
                    log.error(formatSql.toString());
                } else {
                    log.info("Execute {}ms,Mapper:{}", timing, mapperId);
                }
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String maxTime = prop.getProperty("maxTime");
        String format = prop.getProperty("format");
        if (StringUtils.isNotBlank(maxTime)) {
            this.maxTime = Long.parseLong(maxTime);
        }
        if (StringUtils.isNotBlank(format)) {
            this.format = Boolean.parseBoolean(format);
        }
    }

    /**
     * 获取此方法名的具体 Method
     *
     * @param clazz      class 对象
     * @param methodName 方法名
     * @return 方法
     */
    public Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }
}
