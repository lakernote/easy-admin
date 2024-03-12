package com.laker.admin.config;

import com.laker.admin.framework.ext.transaction.LakerDataSourceTransactionManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class LakerTransactionManagerConfig {

    @Bean
    DataSourceTransactionManager transactionManager(DataSource dataSource,
                                                    ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        DataSourceTransactionManager transactionManager = new LakerDataSourceTransactionManager(dataSource, 100);
        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
        return transactionManager;
    }


}
