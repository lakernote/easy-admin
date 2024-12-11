package com.laker.admin.framework.aop.repeatedsubmit;

public interface EasyRepeatSubmiter {
    boolean tryAcquire(String key, long time);
}
