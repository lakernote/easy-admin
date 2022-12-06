package com.laker.admin.framework.ext.actuator.metrics.tomcat;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
public class TomcatMetricsAutoConfiguration {

    @Bean
    public ExtTomcatMetricsBinder tomcatMetrics(MeterRegistry meterRegistry) {
        return new ExtTomcatMetricsBinder(meterRegistry);
    }

}