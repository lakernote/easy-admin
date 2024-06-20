package com.laker.admin.framework.kafka.consumer;

import com.laker.admin.framework.kafka.EasyKafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnBean(EasyKafkaConfig.class)
public class EasyKafkaConsumer {


    @KafkaListener(topics = EasyKafkaConfig.TOPIC_NAME, concurrency = "1", groupId = "laker")
    public void listen(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
        final String format = """
                receive message topic:{}, partition:{}, offset:{}
                key:{}, message:{},
                timestamp:{}, timestampType:{}, headers:{}
                """.trim();
        log.info(format,
                consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), consumerRecord.key(), consumerRecord.value(),
                consumerRecord.timestamp(), consumerRecord.timestampType(),
                consumerRecord.headers());
        if ("error".equals(consumerRecord.value())) {
            log.error("test exception");
            throw new RuntimeException("test exception");
        }

        //手动提交
        //enable.auto.commit参数设置成false。
        ack.acknowledge();
    }

}
