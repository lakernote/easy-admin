package com.laker.admin.module.flow.process;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laker.admin.module.sys.entity.SysUser;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流程公共属性，包含数据库公共基础字段和关联属性字段
 *
 * @author laker
 */
@Data
public class BaseFlowStatus {
    /**
     * 当前流程实例id
     */
    private String orderId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建人部门
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createDeptId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

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

    @TableField(exist = false)
    private SysUser createUser;

}
