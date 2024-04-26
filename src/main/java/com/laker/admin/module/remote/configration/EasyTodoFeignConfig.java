package com.laker.admin.module.remote.configration;

import cn.hutool.core.util.IdUtil;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@Slf4j
public class EasyTodoFeignConfig {
    /**
     * 拦截器可以有多个
     */
    @Order(1)
    @Bean
    RequestInterceptor todoTokenRequestInterceptor() {
        return requestTemplate -> {
            String simpleUUID = IdUtil.simpleUUID();
            log.warn("Todo-token:{}", simpleUUID);
            requestTemplate.header("Todo-token", simpleUUID);
        };
    }

}