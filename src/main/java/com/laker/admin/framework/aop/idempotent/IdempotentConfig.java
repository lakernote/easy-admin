package com.laker.admin.framework.aop.idempotent;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 幂等性配置类
 * 支持配置化选择使用 MySQL 或 Redis 实现幂等性处理
 * 通过 easy.idempotent.store-type 属性配置存储类型: mysql 或 redis
 * 默认使用 mysql 实现
 */
@Configuration
public class IdempotentConfig {

    /**
     * 基于Redis的幂等处理器
     * 当配置为redis模式或未指定MySQL实现时启用
     */
    @Bean
    @ConditionalOnProperty(name = "easy.idempotent.store-type", havingValue = "redis")
    public RedisIdempotentHandler redisIdempotentHandler(StringRedisTemplate stringRedisTemplate) {
        return new RedisIdempotentHandler(stringRedisTemplate);
    }

    /**
     * 基于MySQL的幂等处理器
     * 当配置为mysql模式或未指定存储类型时启用
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(IdempotentHandler.class)
    @ConditionalOnProperty(name = "easy.idempotent.store-type", havingValue = "mysql", matchIfMissing = true)
    public MysqlIdempotentHandler mysqlIdempotentHandler(JdbcTemplate jdbcTemplate) {
        return new MysqlIdempotentHandler(jdbcTemplate);
    }
}