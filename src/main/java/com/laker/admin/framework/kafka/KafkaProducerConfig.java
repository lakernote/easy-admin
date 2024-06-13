package com.laker.admin.framework.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnBean(EasyKafkaConfig.class)
@Slf4j
public class KafkaProducerConfig {

    private final EasyKafkaConfig easyKafkaConfig;

    public KafkaProducerConfig(EasyKafkaConfig easyKafkaConfig) {
        this.easyKafkaConfig = easyKafkaConfig;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, easyKafkaConfig.getBootstrapServers());// kafka地址
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // key序列化
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // value序列化
        configProps.put(ProducerConfig.ACKS_CONFIG, "all"); // 确认机制
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3); // 发送失败后的重复发送次数
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // 批量发送的消息byte大小size 默认16KB
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 100); // 发送时间间隔
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1); // 保证消息的顺序性
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // 保证消息的幂等性
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10485760); // 最大请求大小
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); // 缓冲区大小 默认32MB
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // 重试间隔 默认100ms
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 60000); // 最大阻塞时间
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip"); // 压缩类型
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "easy-kafka-producer"); // 客户端id
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaProducerTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());

        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                log.info("ACK from ProducerListener message: {} offset:  {}",
                        producerRecord.value(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
                ProducerListener.super.onError(producerRecord, recordMetadata, exception);
                log.error("Error from ProducerListener message: {} offset:  {}", producerRecord.value(),
                        recordMetadata.offset());
                log.error("Error from ProducerListener exception : {}", exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}