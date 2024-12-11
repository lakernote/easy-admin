package com.laker.admin.framework.aop.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EasyRateLimit {
    int limit() default 3; // 默认限流次数

    long timeout() default 1; // 时间窗口（分钟）

    String key(); // 限流维度：user, ip, system

    String message() default "Rate limit exceeded"; // 自定义异常描述

    EasyRateLimitType type() default EasyRateLimitType.GLOBAL; // 限流维度
}
