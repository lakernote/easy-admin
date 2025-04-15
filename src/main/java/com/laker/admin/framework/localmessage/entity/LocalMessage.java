package com.laker.admin.framework.localmessage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * `event_type` VARCHAR(100) NOT NULL,
 * `next_retry_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
 * `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
 * `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
 */
@Data
public class LocalMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String status;
    private Date createTime;
    private Date updateTime;
    private int retryCount;
    private String param;
}