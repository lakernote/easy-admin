package com.laker.admin.module.task.core;

import com.laker.admin.module.enums.TaskStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
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


    private TaskStateEnum taskState;
    /**
     * 是否启用
     */
    private Boolean enable;

    public TaskDto() {

    }

}
