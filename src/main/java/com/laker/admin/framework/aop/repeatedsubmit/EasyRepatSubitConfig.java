package com.laker.admin.framework.aop.repeatedsubmit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class EasyRepatSubitConfig {

    @Bean
    public EasyRepeatSubmiter repeatSubmiter(@Autowired(required = false) StringRedisTemplate redisTemplate) {
        if (redisTemplate != null) {
            return new RedisRepeatSubmiter(redisTemplate);
        } else {
            return new MemoryRepeatSubmiter();
        }
    }
}
