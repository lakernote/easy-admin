package com.laker.admin.framework.aop.idempotent;

public interface IdempotentHandler {
    boolean checkAndSet(String key, long expireTime);
    void remove(String key);
}    