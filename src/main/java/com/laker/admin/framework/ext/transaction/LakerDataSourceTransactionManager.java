package com.laker.admin.framework.ext.transaction;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;
import java.util.Date;

/**
 * @author: laker
 * @date: 2022/10/17
 **/
@Slf4j
public class LakerDataSourceTransactionManager extends DataSourceTransactionManager {
    ThreadLocal<Transaction> dateThreadLocal = new ThreadLocal<>();

    public LakerDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        String name = definition.getName();
        TraceContext.addSpan("LakerDataSourceTransactionManager.doBegin", SpanType.Transaction);
        dateThreadLocal.set(new Transaction(new Date(), name));
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        TraceContext.stopSpan();
        Transaction transactionT = dateThreadLocal.get();
        log.info("transaction:{},time:{}ms", transactionT.name, DateUtil.between(new Date(), transactionT.begin, DateUnit.MS));
        dateThreadLocal.remove();
    }

    class Transaction {
        Date begin;
        String name;

        Transaction(Date begin, String name) {
            this.begin = begin;
            this.name = name;
        }
    }
}
