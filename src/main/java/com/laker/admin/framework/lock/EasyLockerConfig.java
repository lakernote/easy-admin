package com.laker.admin.framework.lock;

import com.laker.admin.framework.lock.jdbc.MysqlIEasyLocker;
import com.laker.admin.framework.lock.redis.RedisIEasyLocker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author: laker
 * @date: 2022/11/1
 **/
@Configuration
public class EasyLockerConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "mysql", matchIfMissing = true)
    public IEasyLocker mysqlLock(JdbcTemplate jdbcTemplate) {
        return new MysqlIEasyLocker(jdbcTemplate, easyLockTaskThreadPool());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "redis")
    public IEasyLocker redisLock(StringRedisTemplate stringRedisTemplate) {
        return new RedisIEasyLocker(stringRedisTemplate, easyLockTaskThreadPool());
    }

    private ThreadPoolTaskScheduler easyLockTaskThreadPool() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("easy-lock-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
