package com.laker.admin.config;

import com.laker.admin.framework.ext.websocket.LakerChatHandler;
import com.laker.admin.framework.ext.websocket.LakerSessionHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author laker
 */
@Configuration
@EnableWebSocket // 启动Websocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    LakerChatHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket/**")
            // 添加拦截器，可以获取连接的param和 header 用作认证鉴权
            .addInterceptors(new LakerSessionHandshakeInterceptor())
            // 设置运行跨域
            .setAllowedOrigins("*");
    }
       
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置默认会话空闲超时 以毫秒为单位 非正值意味着无限超时，默认值 0 ，默认没10s检查一次空闲就关闭
        container.setMaxSessionIdleTimeout(10 * 1000L);
        // 设置异步发送消息的默认超时时间 以毫秒为单位 非正值意味着无限超时 ，默认值-1，还没看到作用
//        container.setAsyncSendTimeout(10 * 1000L);
        // 设置文本消息的默认最大缓冲区大小 以字符为单位，默认值 8 * 1024
        container.setMaxTextMessageBufferSize(8 * 1024);
        // 设置二进制消息的默认最大缓冲区大小 以字节为单位，默认值 8 * 1024
        container.setMaxBinaryMessageBufferSize(8 * 1024);
        return container;
    }
}
