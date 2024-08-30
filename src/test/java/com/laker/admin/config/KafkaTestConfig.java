package com.laker.admin.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

/**
 * 创建一个测试专用的配置类
 * Kafka测试配置
 */
@TestConfiguration
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
public class KafkaTestConfig {

    /**
     * 创建一个嵌入式Kafka Broker
     */
    @Bean
    public EmbeddedKafkaBroker embeddedKafkaBroker() {

        return new EmbeddedKafkaKraftBroker(1, 1, "test-topic");
    }

}