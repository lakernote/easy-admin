package com.laker.admin.framework.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnBean(EasyKafkaConfig.class)
public class EasyKafkaTopicConfig {

    @Autowired
    private EasyKafkaConfig easyKafkaConfig;

    /**
     * 以编程方式创建主题
     * KafkaAdmin Spring bean，它将自动为所有类型为NewTopic的 bean 添加主题
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, easyKafkaConfig.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    /**
     * 以编程方式创建主题
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic(easyKafkaConfig.getTopic(), 2, (short) 1);
    }
}