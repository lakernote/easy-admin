package com.laker.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "laker")
public class LakerConfig {
    /**
     * log配置
     */
    private String logFilePath = "logs/laker.log";

}