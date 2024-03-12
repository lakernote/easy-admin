package com.laker.admin.module.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.laker.admin.module.enums.TaskStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author laker
 * @since 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;

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

    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否启用
     */
    private Boolean enable;


    private TaskStateEnum taskState;


}
