package com.laker.admin.module.ext.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laker.admin.module.sys.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.snaker.engine.entity.HistoryOrder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author laker
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "leave_id", type = IdType.AUTO)
    private Long leaveId;

    /**
     * 请假天数
     */
    private Integer leaveDay;

    /**
     * 请假原因
     */
    private String leaveReason;

    /**
     * 请假人id
     */
    private Long leaveUserId;

    @TableField(exist = false)
    private SysUser createUser;

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


    private String orderId;

    @TableField(exist = false)
    private HistoryOrder order;


}
