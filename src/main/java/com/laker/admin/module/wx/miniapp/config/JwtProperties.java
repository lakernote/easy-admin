package com.laker.admin.module.wx.miniapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey;
    /**
     * 过期时间，单位秒
     */
    private long expiration;
}
