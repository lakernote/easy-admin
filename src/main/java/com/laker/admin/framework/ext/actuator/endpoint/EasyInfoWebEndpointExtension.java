package com.laker.admin.framework.ext.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扩展现有端点
 */
@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class EasyInfoWebEndpointExtension {

    private final InfoEndpoint delegate;

    public EasyInfoWebEndpointExtension(InfoEndpoint delegate) {
        this.delegate = delegate;
    }


    @ReadOperation
    public WebEndpointResponse<Map<String, Object>> info() {
        Map<String, Object> info = this.delegate.info();
        Integer status = getStatus(info);
        return new WebEndpointResponse<>(info, status);
    }

    private Integer getStatus(Map<String, Object> info) {
        // 如果是 snapshot 则返回 500
        return 200;
    }
}