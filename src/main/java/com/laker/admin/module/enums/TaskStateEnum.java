package com.laker.admin.module.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum TaskStateEnum {
    START(1, "开始"), STOP(0, "暂停");

    TaskStateEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    @EnumValue//标记数据库存的值是code
    private final int code;
    private final String descp;
}
