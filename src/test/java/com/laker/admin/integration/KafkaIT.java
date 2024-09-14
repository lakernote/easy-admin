package com.laker.admin.integration;

import com.laker.admin.framework.kafka.EasyKafkaConfig;
import com.laker.admin.framework.kafka.producer.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 集成测试-测试kafka
 *
 * @SpringBootTest用于加载完整的Spring上下文，进行端到端的集成测试。
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = {EasyKafkaConfig.TOPIC_NAME})
@Slf4j
@ActiveProfiles("test")
class KafkaIT {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    KafkaProducerService kafkaProducerService;

    @Test
    void testKafkaSendReceive() throws InterruptedException {
        String testMessage = "Hello Kafka";
        kafkaProducerService.sendMessage(EasyKafkaConfig.TOPIC_NAME, testMessage);
        // 等待消息被消费
        Thread.sleep(2000);
    }
}