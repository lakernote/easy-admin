package com.laker.admin.framework.aop.ratelimit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.Serializable;

@Configuration
public class RateLimitConfig {

    @Bean
    @ConditionalOnProperty(name = "ratelimit.type", havingValue = "local", matchIfMissing = true)
    public IRateLimit guavaLimiter() {
        return new GuavaRateLimiter();
    }


    @Bean
    @ConditionalOnProperty(name = "ratelimit.type", havingValue = "redis")
    public IRateLimit redisLimiter(RedisTemplate<String, Serializable> limitRedisTemplate) {

        return new RedisRateLimiter(limitRedisTemplate, limitScript());
    }

    @Bean
    @ConditionalOnProperty(name = "ratelimit.type", havingValue = "redis")
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnProperty(name = "ratelimit.type", havingValue = "redis")
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}