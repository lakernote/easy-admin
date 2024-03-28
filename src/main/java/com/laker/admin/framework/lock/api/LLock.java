package com.laker.admin.framework.lock.api;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

/**
 * @author: laker
 * @date: 2022/11/2
 **/
@Data
@Builder
public class LLock {
    /**
     * 锁定的key
     */
    private String key;
    /**
     * 用于检查是否是这个锁，防止误删
     */
    private String token;
    private ScheduledFuture<?> scheduledFuture;
}
