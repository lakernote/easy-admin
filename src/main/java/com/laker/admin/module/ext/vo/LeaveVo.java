package com.laker.admin.module.ext.vo;

import lombok.Data;

/**
 * @author : [laker]
 * @className : LeaveVo
 * @description : [描述说明该类的功能]
 * @date : 2022年05月08日 16:18
 * @createTime : [2022/5/8 16:18]
 */
@Data
public class LeaveVo {
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
