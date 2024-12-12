package com.laker.admin.module.wx.miniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laker.admin.module.wx.miniapp.dto.WxUserBase;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WxUser extends WxUserBase {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String unionId;

    private String sessionKey;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
