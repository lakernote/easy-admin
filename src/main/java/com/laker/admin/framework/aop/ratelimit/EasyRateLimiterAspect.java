package com.laker.admin.framework.aop.ratelimit;

import cn.dev33.satoken.stp.StpUtil;
import com.laker.admin.utils.http.EasyHttpServletRequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EasyRateLimiterAspect {

    private final EasyRateLimiterFactory easyRateLimiterFactory;

    public EasyRateLimiterAspect(EasyRateLimiterFactory easyRateLimiterFactory) {
        this.easyRateLimiterFactory = easyRateLimiterFactory;
    }

    @Around("@annotation(rateLimit)")
    public Object handleRateLimit(ProceedingJoinPoint joinPoint, EasyRateLimit rateLimit) throws Throwable {
        String key = generateKey(rateLimit); // 动态生成 key
        EasyRateLimiter rateLimiter = easyRateLimiterFactory.getRateLimiter(); // 可切换策略

        if (!rateLimiter.tryAcquire(key, rateLimit.limit(), rateLimit.timeout())) {
            throw new RateLimitException(rateLimit.message()); // 使用注解中的自定义消息
        }
        return joinPoint.proceed();
    }

    private String generateKey(EasyRateLimit rateLimit) {
        String key = rateLimit.key();
        // 根据限流类型生成键
        return switch (rateLimit.type()) {
            case CLIENT_IP -> key + ":" + getClientIp();
            case USER -> key + ":" + getUserId();
            default -> key + ":" + "global";
        };
    }

    // 获取客户端 IP 地址
    private String getClientIp() {
        return EasyHttpServletRequestUtil.getRemoteIP();
    }

    private String getUserId() {
        return StpUtil.getLoginIdAsString();
    }
}
