package com.laker.admin.module.wx.miniapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /**
     * 密钥
     */
    private String secretKey;
    /**
     * 过期时间
     */
    private Duration expiration = Duration.ofMinutes(30);
    /**
     * 刷新过期时间
     */
    private Duration refreshExpiration = Duration.ofDays(7);
}
