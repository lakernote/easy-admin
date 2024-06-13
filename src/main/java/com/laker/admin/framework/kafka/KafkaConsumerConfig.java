package com.laker.admin.framework.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.net.SocketTimeoutException;
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

//        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }


    /**
     * 阻塞重试
     * 如果初始尝试因临时错误而失败，则阻塞重试可让消费者再次尝试使用消息。消费者会等待一段时间（称为重试退避期），然后再尝试再次使用消息。
     * 此外，消费者可以使用固定延迟或指数退避策略自定义重试退避期。它还可以设置在放弃并将消息标记为失败之前的最大重试次数。
     * <p>
     * 阻止重试可能会导致消息处理管道延迟。这会影响系统的整体性能
     * 非阻塞重试 @RetryableTopic
     * 非阻塞重试允许消费者异步重试消息的消费，而不会阻塞消息监听器方法的执行。
     *
     * @param template
     * @return
     */
    @Lazy
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<?, ?> template) {
        log.warn("errorHandler begin to Handle");
        // 设置重试间隔 1秒 次数为 3次
        BackOff fixedBackOff = new FixedBackOff(1000L, 3L);
        // consumerRecord：表示导致错误的 Kafka 记录
        // exception：表示抛出的异常
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
//            // logic to execute when all the retry attemps are exhausted
//        }, fixedBackOff);
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, fixedBackOff);
        // 设置可重试异常与不可重试异常
        errorHandler.addRetryableExceptions(SocketTimeoutException.class);
        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }
}