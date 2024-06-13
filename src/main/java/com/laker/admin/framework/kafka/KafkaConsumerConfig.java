package com.laker.admin.framework.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@ConditionalOnBean(EasyKafkaConfig.class)
public class KafkaConsumerConfig {
    private final EasyKafkaConfig easyKafkaConfig;

    public KafkaConsumerConfig(EasyKafkaConfig easyKafkaConfig) {
        this.easyKafkaConfig = easyKafkaConfig;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, easyKafkaConfig.getBootstrapServers()); // kafka地址
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "laker_clientId"); // 客户端id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "laker_groupId"); // 消费者组
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // key序列化
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // value序列化
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // latest（从最新的消息开始消费）,earliest（从最老的消息开始消费）,none（如果无offset就抛出异常）
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 是否自动提交
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100); // 自动提交的时间间隔
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100); // 每次拉取的最大记录数
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1); // 每次拉取的最小字节数
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500); // 每次拉取的最大等待时间
        // 心跳数,如果消息队列中没有消息，等待毫秒后，调用poll()方法。
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        // 最大轮询间隔，避免消费者被认定为不活跃
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30000); // 最大轮询间隔
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000); // 连接超时时间
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000); // 请求超时时间
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // 为监听器添加消息过滤器
//        factory.setRecordFilterStrategy(
//                record -> record.value().contains("World"));

        // 启用批量消费
        // 设置为 true 表示消息监听器将以批量的方式处理接收到的消息，从而提高消费效率。
        // 设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
//        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(5000);

        // 设置手动提交offset
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // 设置并发消费者数量 小于或等于Topic的分区数
        // 设置了并发消费者数量为 3。这表示每个监听容器会创建 3 个消费者实例来并发地处理消息，
        // 以提高消息处理的吞吐量。这里要看你的分区数是多少，如果是3那么刚好，如果是2，就有多余，浪费。
//        factory.setConcurrency(3);
        return factory;
    }

//    @Bean
//    @Primary
//    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
//
//        log.warn("kafkaErrorHandler begin to Handle");
//
//        // <1> 创建 DeadLetterPublishingRecoverer 对象
//        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
//        // <2> 创建 FixedBackOff 对象   设置重试间隔 10秒 次数为 3次
//        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
//        // <3> 创建 SeekToCurrentErrorHandler 对象
//        return new SeekToCurrentErrorHandler(recoverer, backOff);
//    }

}