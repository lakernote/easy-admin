package com.laker.admin.framework.aop.ratelimit;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.utils.http.EasyRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {

    private final IRateLimit iRateLimit;

    public RateLimitAspect(IRateLimit iRateLimit) {
        this.iRateLimit = iRateLimit;
    }

    @Pointcut("@annotation(com.laker.admin.framework.aop.ratelimit.RateLimit)")
    private void check() {
    }

    @Before("check()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        LimitType limitType = rateLimit.limitType();
        String key = rateLimit.key() + StringUtils.upperCase(method.getName());
        switch (limitType) {
            case IP:
                key = StrUtil.join("-", EasyRequestUtil.getRemoteIP(), key);
                break;
            case USER:
                key = StrUtil.join("-", StpUtil.getLoginId(), key);
                break;
            default:
                break;
        }
        Limiter limiter = Limiter.builder().limitNum(rateLimit.limit())
                .seconds(rateLimit.period())
                .key(key).build();
        if (!iRateLimit.tryAcquire(limiter)) {
            throw new RateLimitException(rateLimit.msg());
        }
    }
}
