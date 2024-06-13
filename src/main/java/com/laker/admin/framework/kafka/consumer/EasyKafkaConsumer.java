package com.laker.admin.framework.kafka.consumer;

import cn.hutool.core.util.RandomUtil;
import com.laker.admin.framework.kafka.EasyKafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@ConditionalOnBean(EasyKafkaConfig.class)
public class EasyKafkaConsumer {

//    @KafkaListener(topics = EasyKafkaConfig.TOPIC_NAME, groupId = "foo")
//    public void listenGroupFoo(String message) {
//        log.info("Received Message in group foo: " + message);
//    }

//    @KafkaListener(topics = EasyKafkaConfig.TOPIC_NAME, groupId = "foo")
//    public void listenWithHeaders(Message<String> message) {
//        log.info("Received Message: " + message.getPayload() + " with headers: " + message.getHeaders());
//    }

//    @KafkaListener(topics = EasyKafkaConfig.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
//    public void listen(String msgData, Acknowledgment ack) {
//        //手动提交
//        //enable.auto.commit参数设置成false。那么就是Spring来替为我们做人工提交，从而简化了人工提交的方式。
//        //所以kafka和springboot结合中的enable.auto.commit为false为spring的人工提交模式。
//        //enable.auto.commit为true是采用kafka的默认提交模式。
//        ack.acknowledge();
//    }

//    @KafkaListener(topicPattern = ".*")
//    public void consumer(ConsumerRecord<?, ?> consumerRecord) {
//        log.info("topic = {}, partition = {}, offset = {}, key = {}, value = {}",
//                consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset(),
//                consumerRecord.key(), consumerRecord.value());
//
//        log.info("received from Kafka broker = {}", consumerRecord.value());
//    }


    /**
     * 使用 Spring RetryableTopic 和 DltHandler 注解来实现 Kafka 消息重试和死信队列功能
     * dlt topic : lakernote-dlt
     * retry topic : lakernote-retry
     * 当消息无法传递到其预期主题时，它将被自动发送到重试主题进行重试。
     * <p>
     * 如果在最大尝试次数后仍无法传递消息，则会将其发送到 DLT 进行进一步处理。
     */
    @RetryableTopic(
            backoff = @Backoff(value = 6000), // 配置重试间隔为 6000 毫秒（6秒）
            attempts = "3", // 设置最大重试次数为 3 次。
            autoCreateTopics = "true", // 如果禁止自动创建重试和死信队列主题，需要手动创建。
            retryTopicSuffix = "-retry", // 重试队列的后缀为 "-retry"。
            dltTopicSuffix = "-dlt", // 死信队列的后缀为 "-dlt"。
            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.MULTIPLE_TOPICS, // 使用单一主题的策略来重试消息。
            exclude = {NullPointerException.class} // 排除空指针异常，不进行重试。
            // 在处理消息时，如果发生 NullPointerException，不进行重试，直接将消息发送到死信队列。
    )
    @KafkaListener(topics = EasyKafkaConfig.TOPIC_NAME)
    public void consume(ConsumerRecord<String, String> record, @Headers MessageHeaders headers) {
        log.info("### -> Header acquired: {}", headers);
        Acknowledgment ack = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        log.info(String.format("#### -> Consumed message -> %s", record.value()));
        log.info("#### -> Key: {}", record.key());
        if (RandomUtil.randomBoolean()) {
            throw new RuntimeException("模拟异常");
        }
        if (Objects.nonNull(ack)) {
            ack.acknowledge(); // 手动确认消息
        }

    }

    /**
     * 用于处理死信队列中的消息
     */
    @DltHandler
    public void dlt(String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Event from topic [{}]  is dead lettered - event:{}", topic, data);
    }
}
