package com.laker.admin.framework.aop.ratelimit;

public interface EasyRateLimiter {
    /**
     * 尝试获取令牌,不会阻塞,获取成功返回 true,否则返回 false
     *
     * @param key        限流业务标识
     * @param limit      限流次数
     * @param timeWindow 时间窗口,单位: 秒
     * @return 是否获取成功
     */
    boolean tryAcquire(String key, int limit, long timeWindow);
}
