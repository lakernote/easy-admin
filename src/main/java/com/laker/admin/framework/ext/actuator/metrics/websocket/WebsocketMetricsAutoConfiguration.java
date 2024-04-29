package com.laker.admin.framework.ext.actuator.metrics.websocket;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}