package com.laker.admin.framework.ext.transaction;

import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

/**
 * 长事务监控
 *
 * @author: laker
 * @date: 2022/10/17
 **/
@Slf4j
public class LakerDataSourceTransactionManager extends DataSourceTransactionManager {


    transient ThreadLocal<LinkedList<Transaction>> dateThreadLocal = new ThreadLocal<>();
    private transient long costTime;

    public LakerDataSourceTransactionManager(DataSource dataSource, long costTime) {
        super(dataSource);
        this.costTime = costTime;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        String name = definition.getName();
        TraceContext.addSpan(name, SpanType.Transaction);
        List<Transaction> transactionList = dateThreadLocal.get();
        // init
        if (CollectionUtils.isEmpty(transactionList)) {
            LinkedList<Transaction> linkedList = new LinkedList<>();
            linkedList.add(new Transaction(System.currentTimeMillis(), name));
            dateThreadLocal.set(linkedList);
        } else {
            transactionList.add(new Transaction(System.currentTimeMillis(), name));
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        LinkedList<Transaction> transactions = dateThreadLocal.get();
        if (CollectionUtils.size(transactions) > 1) {
            transactions.removeLast();
        } else {
            Transaction transaction1 = transactions.get(0);
            long cost = System.currentTimeMillis() - transaction1.begin;
            dateThreadLocal.remove();
            if (cost > costTime) {
                log.debug("事务耗时监控！transaction:{},time:{}ms", transaction1.name, cost);
            }
        }
        super.doCleanupAfterCompletion(transaction);
        TraceContext.stopSpan(costTime);
    }

    class Transaction {
        long begin;
        String name;

        Transaction(long begin, String name) {
            this.begin = begin;
            this.name = name;
        }
    }
}
