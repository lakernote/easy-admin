package com.laker.admin.framework.aop.ratelimit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Limiter {

    private int limitNum;
    private int seconds;
    private String key;
}
