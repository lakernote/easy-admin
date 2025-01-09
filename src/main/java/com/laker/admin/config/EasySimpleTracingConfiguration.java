package com.laker.admin.config;

import brave.handler.MutableSpan;
import brave.handler.SpanHandler;
import brave.propagation.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EasySimpleTracingConfiguration {
    // 关键组件 BraveAutoConfiguration.java MicrometerTracingAutoConfiguration.java
    // - Tracer  Brave / OpenTracing 实现
    // - Propagator Brave / OpenTracing 实现
    // - SpanHandler 输出Span信息

    // - MicrometerObservationCapability feign
    // - ObservationGrpcClientCall grpc
    // - ObservationExecChainHandler httpclient
    // - OkHttpObservationInterceptor okhttp
    // - MicrometerTracingAdapter redis
    // - KafkaTemplateObservation kafka
    // - TrackingSubscriber scheduler
    // - ClientHttpObservationDocumentation restclient
    // - ServerHttpObservationFilter server
    // - AspectJ 方面用于拦截用 @Observed 注释的类型或方法。

    // 默认是Tracer.NOOP，添加了Brave到classpath后，默认是BraveTracer
    // trace协议/追踪实现 + 存储 Zipkin / Jaeger / Console Logging 等追踪后端
    // SpanHandler LogSpanHandler 会将Span信息输出到日志
    // ConsoleSpanHandler 会将Span信息输出到控制台
    // BraveAutoConfiguration
    // from Tracing LogSpanHandler

    // JDBC trace https://github.com/jdbc-observations/datasource-micrometer

//    @Bean
    //  开启会把所有的span信息输出到日志
    public SpanHandler logSpanHandler() {
        return new LogSpanHandler();
    }

    static final class LogSpanHandler extends SpanHandler {

        @Override
        public boolean end(TraceContext context, MutableSpan span, Cause cause) {
            log.info(span.toString());
            return true;
        }
    }

    // PropagatingSenderTracingObservationHandler

    // CurrentTraceContext.decorateScope 中在MDC设置traceId / spanId
    // - CorrelationScopeDecorator MDCScopeDecorator
}
