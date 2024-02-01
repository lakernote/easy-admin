package com.laker.admin.framework.aop.ratelimit;

public class RateLimitException extends RuntimeException {

    public RateLimitException(String msg) {
        super(msg);
    }

}