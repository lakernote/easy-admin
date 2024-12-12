package com.laker.admin.module.wx.miniapp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WxUserTypeEnum {
    MEMBER("MEMBER", "会员"),
    GUEST("GUEST", "游客");

    @EnumValue
    private final String dict;
    private final String desc;

    WxUserTypeEnum(String dict, String desc) {
        this.dict = dict;
        this.desc = desc;
    }
}
