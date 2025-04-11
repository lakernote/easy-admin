package com.laker.admin.framework.localmessage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

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