package com.laker.admin.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 为所有断路器提供默认配置
 */
@Configuration
@Slf4j
public class EasyCircuitBreakerConfig {

    /**
     * 为所有【工厂编码形式】的断路器提供默认配置
     * - FeignCircuitBreakerTargeter
     * - feign和注解的只能走yaml配置文件
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> {
            // 默认断路器配置
            factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .build());

            // 特定断路器配置
            factory.configure(builder -> builder.circuitBreakerConfig(
                                    CircuitBreakerConfig.custom()
                                            // 断路器的滑动窗口期类型。可以基于"次数"(COUNT_BASED)或者"时间"(TIME_BASED)进行熔断
                                            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                                            // 以百分比配置失败率峰值。以百分比配置失败率阈值。当失败率等于或者大于阈值时，断路器状态变关闭为开启，并进行服务降级(fallback)
                                            .failureRateThreshold(50)
                                            // 配置调用时间的峰值，高于该峰值的视为慢调用。
                                            .slowCallDurationThreshold(Duration.ofSeconds(2))
                                            // 以百分比的方式配置，断路器把调用时间大于slowCallDurationThreshold的调用视为慢调用，当慢调用比例大于等于峰值时，断路器开启，并进入服务降级。
                                            .slowCallRateThreshold(50)
                                            // 若COUNT_BASED，则10次调用中有50%失败（即5次）打开熔断断路器；若为TIME_BASED则，此时还有额外的两个设置属性，含义为：在N秒内（sliding-window-size）100%（slow-call-rate-threshold）的请求超过N秒（slow-call-duration-threshold）打开断路器。
                                            .slidingWindowSize(5) // 窗口大小
                                            // 运行断路器在HALF_OPEN状态下时进行N次调用，如果故障或慢速调用仍然高于阈值，断路器再次进入打开状态。
                                            .permittedNumberOfCallsInHalfOpenState(2) // 半开状态下允许的请求数
                                            // 在每个滑动窗口期样本数，配置断路器计算错误率或者慢调用率的最小调用数。比如设置为5意味着，在计算故障率之前，必须至少调用5次。如果只记录了4次，即使4次都失败了，断路器也不会进入到打开状态。
                                            .minimumNumberOfCalls(5) // 最小请求数
                                            // 从OPEN到HALF_OPEN状态需要等待的时间 断路器打开后等待时间,会在指定的等待时间内拒绝所有请求。等待时间结束后，断路器会进入半开状态，允许部分请求通过以测试服务是否恢复正常。
                                            .waitDurationInOpenState(Duration.ofSeconds(10))
                                            .build())
                            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(1)).build()),
                    "slow","ipifyClient");

            // 添加事件处理
            factory.addCircuitBreakerCustomizer(circuitBreaker -> circuitBreaker.getEventPublisher()
                            .onError(event -> log.error("circuit error", event.getThrowable()))
                            .onSuccess(event -> log.info("normalflux success")),
                    "slow");

        };
    }

//    @Bean
//    public Customizer<Resilience4jBulkheadProvider> defaultBulkheadCustomizer() {
//        return provider -> provider.configureDefault(id -> new Resilience4jBulkheadConfigurationBuilder()
//                .bulkheadConfig(BulkheadConf.custom().maxConcurrentCalls(4).build())
//                .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.custom().coreThreadPoolSize(1).maxThreadPoolSize(1).build())
//                .build()
//        );
//    }

}

