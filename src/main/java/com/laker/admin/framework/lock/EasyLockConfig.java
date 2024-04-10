package com.laker.admin.framework.lock;

import com.laker.admin.framework.lock.api.ILock;
import com.laker.admin.framework.lock.impl.jdbc.MysqlLock;
import com.laker.admin.framework.lock.impl.redis.RedisILock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;

/**
 * @author: laker
 * @date: 2022/11/1
 **/
@Configuration
public class EasyLockConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "mysql", matchIfMissing = true)
    public ILock mysqlLock(JdbcTemplate jdbcTemplate, TaskScheduler taskScheduler) {
        return new MysqlLock(jdbcTemplate, taskScheduler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "redis")
    public ILock redisLock(StringRedisTemplate stringRedisTemplate, TaskScheduler taskScheduler) {
        return new RedisILock(stringRedisTemplate, taskScheduler);
    }
}
