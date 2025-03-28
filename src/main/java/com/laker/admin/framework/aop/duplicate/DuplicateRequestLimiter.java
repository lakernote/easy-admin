package com.laker.admin.framework.aop.duplicate;

/**
 * 重复请求限制器
 */
public interface DuplicateRequestLimiter {
    /**
     * 尝试提交请求，若返回 true 则表示可以执行，false 表示被拦截
     *
     * @param key     请求 key
     * @param timeout 超时时间（秒）
     * @return 是否允许执行请求
     */
    boolean tryRequest(String key, long timeout);
}
