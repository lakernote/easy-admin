package com.laker.admin.framework.aop.ratelimit;

/**
 * 限流类型
 */
public enum LimitType {
    /**
     * 全局限流
     */
    ALL,
    /**
     * 根据请求者IP限流
     */
    IP,
    /**
     * 根据请求者限流
     */
    USER
}