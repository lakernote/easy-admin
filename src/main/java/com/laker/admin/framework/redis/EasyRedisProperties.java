package com.laker.admin.framework.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置
 *
 * @author laker
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "easy.spring.redis")
@ConditionalOnProperty(prefix = "easy.spring.redis", name = "enabled", havingValue = "true")
public class EasyRedisProperties {
    private boolean enabled = false;
    private String address = "redis://localhost:6379";
    private String password;
    private int database = 0;
    private String clientName = "easy-admin";

}