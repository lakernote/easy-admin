package com.laker.admin.framework.aop.ratelimit;

public interface IRateLimit {
    boolean tryAcquire(Limiter limiter);
}
