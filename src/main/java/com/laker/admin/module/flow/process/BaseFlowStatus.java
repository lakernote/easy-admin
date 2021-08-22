package com.laker.admin.module.flow.process;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author laker
 */
@Data
public class BaseFlowStatus {
    /**
     * 当前流程实例id
     */
    private String orderId;
    /**
     * 当前流程所处任务节点名称
     */
    @TableField(exist = false)
    private String taskName;

    /**
     * 第一个任务id，用于撤回流程
     */
    @TableField(exist = false)
    private String createTaskId;
    /**
     * 当前流程所处任务操作人名称
     */
    @TableField(exist = false)
    private String taskOperatorName;
    /**
     * 当前流程所处任务操作人
     */
    @TableField(exist = false)
    private String taskOperatorId;
    /**
     * 流程实例状态（0：结束；1：活动）
     */
    @TableField(exist = false)
    private Integer orderState;
}
