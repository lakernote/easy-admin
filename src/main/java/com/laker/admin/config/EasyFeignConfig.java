package com.laker.admin.config;

import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.EasyAdminConstants;
import feign.*;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Feign 全局配置
 */
@Configuration
@EnableFeignClients(basePackages = "com.laker.admin.module.remote") // 启用feign 指定扫描的包
@Slf4j
public class EasyFeignConfig {
    /**
     * 拦截器可以有多个
     */
    @Order(1)
    @Bean
    RequestInterceptor traceIdRequestInterceptor() {
        return requestTemplate -> {
            if (Optional.ofNullable(MDC.get(EasyAdminConstants.TRACE_ID)).isEmpty()) {
                MDC.put(EasyAdminConstants.TRACE_ID, IdUtil.simpleUUID());
            }
            log.info("traceIdRequestInterceptor:{}", MDC.get(EasyAdminConstants.TRACE_ID));
            requestTemplate.header(EasyAdminConstants.TRACE_ID, MDC.get(EasyAdminConstants.TRACE_ID));
        };
    }

    @Order(2)
    @Bean
    RequestInterceptor viaRequestInterceptor() {
        return requestTemplate -> {
            log.info("viaRequestInterceptor:{}", MDC.get(EasyAdminConstants.TRACE_ID));
            requestTemplate.header("via", "easy-feign");
        };
    }


    @Bean
    Request.Options feignOptions() {
        return new Request.Options(5, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true);
    }

    @Bean
    Logger.Level feignLogger() {
        // 记录请求和响应的标题，正文和元数据
        // 需要配置
        // com.laker.admin.module.remote : DEBUG
        return Logger.Level.FULL;
    }

    /**
     * 在Feign重试行为中，它将自动重试IOException，将它们视为与网络临时相关的异常，
     * 以及从ErrorDecoder抛出的任何RetryableException。
     * 这里可以在ErrorDecoder自定义逻辑根据是否抛出 RetryableException异常来自定义重试逻辑，nice的一比。
     */
    @Bean
    Retryer feignRetryer() {
        //最大请求次数为3，初始间隔时间为100ms，下次间隔时间1.5倍递增，重试间最大间隔时间为1s，
        return new Retryer.Default(100, 1000, 3);
    }

    @Bean
    public ErrorDecoder feignError() {
        // ErrorDecoder的默认实现
        // 响应包含“Retry-After”标头时创建RetryableException实例。
        // 最常见的是，我们可以在 503 服务不可用响应中找到这个标头。
        final ErrorDecoder errorDecoder = new ErrorDecoder.Default();
        return (methodKey, response) -> {

            if (response.status() == 400) {
                log.error("请求xxx服务400参数错误,返回:{}", response.body());
            }

            if (response.status() == 404) {
                log.error("请求xxx服务404异常,返回:{}", response.body());
            }

            Exception defaultException = errorDecoder.decode(methodKey, response);
            if (defaultException instanceof RetryableException) {
                // Requirement 3: retry when Retry-After header is set
                // 默认的重试逻辑
                return defaultException;
            }

            // 扩展的重试逻辑
            if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
                return new RetryableException(
                        response.status(),
                        defaultException.getMessage(),
                        response.request().httpMethod(),
                        defaultException,
                        (Long) null,
                        response.request());
            }

            // 默认处理
            return defaultException;
        };
    }

}
