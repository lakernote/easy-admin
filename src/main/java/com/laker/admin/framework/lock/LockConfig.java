package com.laker.admin.framework.lock;

import com.laker.admin.framework.lock.api.Lock;
import com.laker.admin.framework.lock.impl.jdbc.MysqlLock;
import com.laker.admin.framework.lock.impl.redis.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author: laker
 * @date: 2022/11/1
 **/
@Configuration
public class LockConfig {

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix("lakerscheduler-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "mysql", matchIfMissing = true)
    public Lock mysqlLock(JdbcTemplate jdbcTemplate, TaskScheduler taskScheduler) {
        return new MysqlLock(jdbcTemplate, taskScheduler);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "lock.type", havingValue = "redis")
    public Lock redisLock(StringRedisTemplate stringRedisTemplate, TaskScheduler taskScheduler) {
        return new RedisLock(stringRedisTemplate, taskScheduler);
    }
}
