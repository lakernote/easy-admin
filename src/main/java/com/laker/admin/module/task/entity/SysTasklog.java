package com.laker.admin.module.task.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTasklog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tasklog_id", type = IdType.AUTO)
    private Long tasklogId;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 状态 1正常，2异常
     */
    private Integer status;

    /**
     * 耗时 ms
     */
    private Integer cost;

    private String threadName;


}
