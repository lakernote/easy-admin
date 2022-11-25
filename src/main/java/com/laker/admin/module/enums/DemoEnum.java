package com.laker.admin.module.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.laker.admin.framework.ext.mvc.IEnumConvert;
import lombok.Getter;

@Getter
public enum DemoEnum implements IEnumConvert {
    START(1, "start"), STOP(0, "stop");

    DemoEnum(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    private final int code;
    private final String descp;

    @Override
    @JsonValue
    public String getValue() {
        return descp;
    }
}
