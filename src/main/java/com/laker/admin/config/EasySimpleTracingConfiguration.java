package com.laker.admin.config;

import brave.Tracer;
import brave.handler.MutableSpan;
import brave.handler.SpanHandler;
import brave.propagation.TraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class EasySimpleTracingConfiguration {
    // 默认是Tracer.NOOP，添加了Brave到classpath后，默认是BraveTracer
    // trace协议/追踪实现 + 存储 Zipkin / Jaeger / Console Logging 等追踪后端
    // SpanHandler LogSpanHandler 会将Span信息输出到日志
    // ConsoleSpanHandler 会将Span信息输出到控制台
    // BraveAutoConfiguration
    // from Tracing LogSpanHandler


    @Bean
    //  开启会把所有的span信息输出到日志
    public SpanHandler logSpanHandler() {
        return new LogSpanHandler();
    }

    static final class LogSpanHandler extends SpanHandler {
        final Logger logger = Logger.getLogger(Tracer.class.getName());

        @Override
        public boolean end(TraceContext context, MutableSpan span, Cause cause) {
            if (!logger.isLoggable(Level.INFO)) return false;
            logger.info(span.toString());
            return true;
        }

        @Override
        public String toString() {
            return "LogSpanHandler{name=" + logger.getName() + "}";
        }
    }
}
