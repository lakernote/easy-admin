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
                                    .failureRateThreshold(50) // 50% 失败率阈值
                                    // 断路器打开后等待时间,会在指定的等待时间内拒绝所有请求。等待时间结束后，断路器会进入半开状态，允许部分请求通过以测试服务是否恢复正常。
                                    .waitDurationInOpenState(Duration.ofSeconds(10))
                                    .permittedNumberOfCallsInHalfOpenState(2) // 半开状态下允许的请求数
                                    .slidingWindowSize(5) // 窗口大小
                                    .minimumNumberOfCalls(5) // 最小请求数
                                    .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(1)).build()), "slow");

            // 添加事件处理
            factory.addCircuitBreakerCustomizer(circuitBreaker -> circuitBreaker.getEventPublisher()
                    .onError(event -> log.error("circuit error", event.getThrowable()))
                    .onSuccess(event -> log.info("normalflux success")), "slow");

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

