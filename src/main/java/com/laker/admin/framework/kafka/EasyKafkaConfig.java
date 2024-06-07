package com.laker.admin.framework.kafka;

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
@ConfigurationProperties(prefix = "easy.spring.kafka")
@ConditionalOnProperty(prefix = "easy.spring.kafka", name = "enabled", havingValue = "true")
public class EasyKafkaConfig {

    public static final String TOPIC_NAME = "lakernote";
    private boolean enabled = false;
    private String bootstrapServers = "localhost:9092";
    private String topic = TOPIC_NAME;

}