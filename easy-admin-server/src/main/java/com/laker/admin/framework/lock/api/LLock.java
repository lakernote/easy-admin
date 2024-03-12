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
    private String key;
    private String token;
    private ScheduledFuture<?> scheduledFuture;
}
