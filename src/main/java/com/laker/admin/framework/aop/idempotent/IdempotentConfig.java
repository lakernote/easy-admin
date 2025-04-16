package com.laker.admin.framework.aop.idempotent;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class IdempotentConfig {

    @Bean
    public IdempotentHandler idempotentHandler(JdbcTemplate jdbcTemplate) {
        // 这里可以根据需要选择使用 Redis 或 MySQL 实现
        // return new RedisIdempotentHandler();
        return new MysqlIdempotentHandler(jdbcTemplate);
    }
}
