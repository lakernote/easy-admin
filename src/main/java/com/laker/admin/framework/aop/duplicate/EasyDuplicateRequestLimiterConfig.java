package com.laker.admin.framework.aop.duplicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class EasyDuplicateRequestLimiterConfig {

    @Bean
    public DuplicateRequestLimiter duplicateRequestLimiter(
            @Autowired(required = false) StringRedisTemplate redisTemplate) {
        // 如果 RedisTemplate 存在，则使用 RedisDuplicateRequestLimiter
        if (redisTemplate != null) {
            return new RedisDuplicateRequestLimiter(redisTemplate);
        } else { // 否则使用 ConcurrentHashMapDuplicateRequestLimiter
            return new ConcurrentHashMapDuplicateRequestLimiter();
        }
    }
}
