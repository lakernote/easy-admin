package com.laker.admin.framework.aop.ratelimit;

public class RateLimitException extends RuntimeException {
    public RateLimitException(String message) {
        super(message);
    }
}