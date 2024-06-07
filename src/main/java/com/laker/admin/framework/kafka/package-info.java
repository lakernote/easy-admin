package com.laker.admin.framework.kafka;
/**
 * <p>
 * https://spring.io/projects/spring-kafka
 * KafkaTemplate 用于向 Kafka 主题发送消息的高级抽象。它通过提供异步或同步发送消息的方法简化了生成消息的过程。
 * KafkaMessageListenerContainer
 * KafkaListener 用于在 Spring 组件中定义消息侦听器方法的注释。注释的方法会@KafkaListener自动订阅 Kafka 主题并从中接收消息。
 * KafkaTransactionManager
 * spring-kafka-test带有嵌入式 kafka 服务器的 jar
 * ProducerFactory：用于创建 Kafka 生产器实例的工厂接口。它用于配置生产器属性并在将消息负载发送到 Kafka 之前对其进行序列化。
 * ConsumerFactory：用于创建 Kafka 消费者实例的工厂接口。它用于配置消费者属性并反序列化从 Kafka 收到的消息负载。
 * ConcurrentMessageListenerContainer：消息侦听器实例的容器，允许并发使用来自 Kafka 主题的消息。它提供配置并发、错误处理和消息确认的选项。
 * https://github.com/spring-projects/spring-kafka/tree/main/samples
 * - 具有死信主题的简单生产者/消费者
 * - 多方法监听器
 * - 事务
 * - 基于主题（非阻塞）重试
 * - spring-kafka 中的新消费者重新平衡协议
 * https://docs.spring.io/spring-kafka/reference/kafka/exactly-once.html
 * </p>
 */