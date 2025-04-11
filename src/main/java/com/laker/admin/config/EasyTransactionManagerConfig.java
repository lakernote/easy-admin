package com.laker.admin.config;

import com.laker.admin.framework.ext.transaction.LakerDataSourceTransactionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class EasyTransactionManagerConfig {

    /**
     * 事务管理器
     */
    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource,
                                                  ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
                                                  EasyAdminConfig easyAdminConfig) {
        TransactionManager transactionManager = new LakerDataSourceTransactionManager(
                dataSource, easyAdminConfig.getTrace().getTransactionTime());
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return (PlatformTransactionManager) transactionManager;
    }

}
