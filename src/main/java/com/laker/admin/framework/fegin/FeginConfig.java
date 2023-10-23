package com.laker.admin.framework.fegin;

import feign.Client;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class FeginConfig {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(Duration.ofSeconds(2), Duration.ofSeconds(5), true));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.hystrix.enabled", matchIfMissing = false)
    public Feign.Builder feignHystrixBuilder() {
        return HystrixFeign
                .builder();
    }

    @Bean
    @ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
    public Client feignClient() {
        return new OkHttpClient();
    }
//    @Bean
//    @ConditionalOnProperty(value = "feign.httpclient.enabled", matchIfMissing = true)
//    public Client feignClient() {
//        return new ApacheHttpClient();
//    }

    @Bean
    public Encoder encoder() {
        return new JacksonEncoder();
    }
    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }
}
