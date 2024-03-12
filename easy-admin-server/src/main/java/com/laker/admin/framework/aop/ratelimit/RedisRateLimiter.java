package com.laker.admin.framework.aop.ratelimit;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.Serializable;

@Slf4j
public class RedisRateLimiter implements IRateLimit {

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    private final DefaultRedisScript<Long> limitScript;

    public RedisRateLimiter(RedisTemplate<String, Serializable> template, DefaultRedisScript<Long> redisScript) {
        limitRedisTemplate = template;
        limitScript = redisScript;
    }

    @Override
    public boolean tryAcquire(Limiter limiter) {
        String key = limiter.getKey();
        int limitNum = limiter.getLimitNum();
        int limitPeriod = limiter.getSeconds();
        Long count = limitRedisTemplate.execute(limitScript, CollUtil.newArrayList(key), limitNum, limitPeriod);
        // 超过限制会返回-1
        boolean access = count == null || count.intValue() != -1;
        log.info("tryAcquire key:{} access :{} count:{}", limiter.getKey(), access, count);
        return access;
    }


}