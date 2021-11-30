package com.laker.admin.module.ext.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.laker.admin.module.flow.process.BaseFlowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class ExtLeave extends BaseFlowStatus implements Serializable {

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

}
