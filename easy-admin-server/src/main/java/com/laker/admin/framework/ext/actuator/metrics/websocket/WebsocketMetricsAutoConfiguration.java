package com.laker.admin.framework.ext.actuator.metrics.websocket;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author laker
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class WebsocketMetricsAutoConfiguration {

    @Bean
    public WebsocketMetrics websocketMetrics(MeterRegistry meterRegistry) {
        return new WebsocketMetrics(meterRegistry);
    }

    /**
     * 日志示例,生产环境放开
     *
     * @return
     */
    // @Bean
    public MeterRegistry meterRegistry() {
        return new LoggingMeterRegistry(new LoggingRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(60);
            }
        }, Clock.SYSTEM);
    }

}