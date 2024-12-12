package com.laker.admin.module.wx.miniapp.dto;

import com.laker.admin.module.wx.miniapp.enums.WxUserTypeEnum;
import lombok.Data;

@Data
public abstract class WxUserBase {

    private String openId;

    private String nickName;

    private WxUserTypeEnum roleType;

    private String avatar;
}
