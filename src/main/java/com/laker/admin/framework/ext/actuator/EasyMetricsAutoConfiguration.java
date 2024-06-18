package com.laker.admin.framework.ext.actuator;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author laker
 */
@Configuration
public class EasyMetricsAutoConfiguration {

    /**
     * 日志示例,生产环境放开
     */
    @Bean
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