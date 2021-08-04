package com.laker.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //为/chatroom路径启用SockJS功能
        registry.addEndpoint("/chatroom").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //表明在topic、queue、users这三个域上可以向客户端发消息。 服务端-->客户端
        registry.enableSimpleBroker("/topic", "/queue", "/users");
        //客户端向服务端发起请求时，需要以/app为前缀。 客户端-->服务端
        registry.setApplicationDestinationPrefixes("/app");
        //给指定用户发送一对一的消息前缀是/users/。
        registry.setUserDestinationPrefix("/users/");
    }
}
