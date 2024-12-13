package com.laker.admin.module.remote.feign;

import com.laker.admin.module.remote.feign.dto.IpifyVo;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.stereotype.Component;

@Component
public class Fallback implements IpifyClient {

    @Override
    public IpifyVo getIpAddress() {
        throw new NoFallbackAvailableException("Boom!", new RuntimeException());
    }
}