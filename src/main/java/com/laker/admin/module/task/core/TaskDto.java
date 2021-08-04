package com.laker.admin.module.task.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

@Data
@Builder
public class TaskDto {
    /**
     * 任务的编码 必须全局唯一
     */
    private String taskCode;
    /**
     * 任务的名称
     */
    private String taskName;
    /**
     * 任务的类名称
     */
    private String taskClassName;
    /**
     * 任务的cron表达式
     */
    private String taskCron;

    @JsonIgnore
    private ScheduledFuture scheduledFuture;

    @JsonIgnore
    private IJob job;
    /**
     * 是否执行完成
     */
    private Boolean done;

    private TaskStateEnum taskState;

    public Boolean getDone() {
        return scheduledFuture == null ? null : scheduledFuture.isDone();
    }
}
