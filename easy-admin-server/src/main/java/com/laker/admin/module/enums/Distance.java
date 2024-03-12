package com.laker.admin.module.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.laker.admin.framework.ext.mvc.IEnum;


public enum Distance implements IEnum<String> {
    KILOMETER("km", 1000),
    MILE("miles", 1609.34),
    METER("meters", 1);
    private String unit;
    private final double meters;

    Distance(String unit, double meters) {
        this.unit = unit;
        this.meters = meters;
    }

    @Override
    @JsonValue
    public String getValue() {
        return unit;
    }
}