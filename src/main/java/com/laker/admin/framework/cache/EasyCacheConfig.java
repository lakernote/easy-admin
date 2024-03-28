package com.laker.admin.framework.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class EasyCacheConfig {

    @Bean
    @ConditionalOnProperty(name = "cache.type", havingValue = "local", matchIfMissing = true)
    public IEasyCache jvmCache() {
        return new EasyMapCache();
    }


    @Bean
    @ConditionalOnProperty(name = "cache.type", havingValue = "redis")
    public IEasyCache redisCache(RedisTemplate<String, Object> cacheRedisTemplate) {
        return new EasyRedisCache(cacheRedisTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "cache.type", havingValue = "redis")
    public RedisTemplate<String, Object> cacheRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
