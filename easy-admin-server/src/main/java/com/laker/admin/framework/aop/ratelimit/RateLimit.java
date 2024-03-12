package com.laker.admin.framework.aop.ratelimit;

import java.lang.annotation.*;

/**
 * <a href="https://blog.csdn.net/abu935009066/article/details/123700329">...</a>
 * 限流注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimit {

    /**
     * 资源的key
     * <p>
     * 例如：limitType=IP.RedisKey为IP+key
     */
    String key() default "";

    /**
     * 给定的时间段 单位秒
     */
    int period() default 10;

    /**
     * 最多的访问限制次数
     */
    int limit() default 10;

    LimitType limitType() default LimitType.IP;

    String msg() default "请求过于频繁，请稍后重试";
}