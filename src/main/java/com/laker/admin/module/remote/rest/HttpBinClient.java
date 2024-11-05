package com.laker.admin.module.remote.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;

/**
 * A simple client for the HttpBin API.
 * - 同步 HTTP 客户端
 * https://httpbin.org/
 * https://jsonplaceholder.typicode.com/
 * https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-restclient
 */
@Slf4j
@Service
public class HttpBinClient {

    /**
     * 可以被多个线程安全地使用
     */
    private final RestClient restClient;

    public HttpBinClient(RestClient.Builder restClientBuilder) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings
                .DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(5))
                .withReadTimeout(Duration.ofSeconds(10));
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);
        this.restClient = restClientBuilder
                .baseUrl("https://httpbin.org/")
                .requestFactory(requestFactory)
                .defaultHeader("User-Agent", "Spring 5 WebClient")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            log.error("Client Error Status {}", response.getStatusCode());
                            log.error("Client Error Body {}", new String(response.getBody().readAllBytes()));
                        })
                // add Authorization
                // 在请求拦截器中增加 验证token
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer my-token");
                    return execution.execute(request, body);
                })
                .build();
    }

    public String get(String name) {
        return this.restClient.get().uri("/get?name={name}&age={age}", name, 30).retrieve().body(String.class);
    }

}