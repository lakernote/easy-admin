package com.laker.admin.framework.ext.transaction;

import cn.hutool.core.collection.CollUtil;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.util.CollectionUtils;

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

    private final transient ThreadLocal<LinkedList<Transaction>> transactionThreadLocal = new ThreadLocal<>();

    private final transient long costTime;

    public LakerDataSourceTransactionManager(DataSource dataSource, long costTime) {
        super(dataSource);
        this.costTime = costTime;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        String name = definition.getName();
        TraceContext.addSpan(name, SpanType.Transaction);
        List<Transaction> transactionList = transactionThreadLocal.get();
        // init
        if (CollectionUtils.isEmpty(transactionList)) {
            LinkedList<Transaction> linkedList = new LinkedList<>();
            linkedList.add(new Transaction(System.currentTimeMillis(), name));
            transactionThreadLocal.set(linkedList);
        } else {
            transactionList.add(new Transaction(System.currentTimeMillis(), name));
        }
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        LinkedList<Transaction> transactions = transactionThreadLocal.get();
        if (CollUtil.size(transactions) > 1) {
            transactions.removeLast();
        } else {
            Transaction transaction1 = transactions.get(0);
            long cost = System.currentTimeMillis() - transaction1.begin;
            transactionThreadLocal.remove();
            if (cost > costTime) {
                log.error("事务耗时监控！transaction:{},time:{}ms", transaction1.name, cost);
            }
        }
        super.doCleanupAfterCompletion(transaction);
        TraceContext.stopSpan(costTime);
    }

    static class Transaction {
        long begin;
        String name;

        Transaction(long begin, String name) {
            this.begin = begin;
            this.name = name;
        }
    }
}
