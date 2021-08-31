package com.laker.admin.config;

import com.laker.admin.framework.ext.stomp.EasyChannelInterceptor;
import com.laker.admin.framework.ext.stomp.EasyHandShakeInterceptor;
import com.laker.admin.framework.ext.stomp.EasyPrincipalHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //为/chatroom路径启用SockJS功能
        registry.addEndpoint("/chatroom")
                .setAllowedOrigins("*")
                // 握手处理，主要是连接的时候认证获取其他数据验证等
                .setHandshakeHandler(new EasyPrincipalHandshakeHandler())
                // 拦截处理，和http拦截类似
                .addInterceptors(new EasyHandShakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //表明在topic、user域上可以向客户端发消息。 服务端-->客户端 广播式使用/topic，点对点式使用/user
        registry.enableSimpleBroker("/topic", "/user");
        //客户端向服务端发起请求时，需要以/app为前缀。 客户端-->服务端
        registry.setApplicationDestinationPrefixes("/app");
        //给指定用户发送一对一的消息前缀是/users/，默认/user/。
        registry.setUserDestinationPrefix("/user/");
    }


//    /**
//     * 设置输入消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
//     *
//     * @param registration
//     */
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // 注册消息拦截器
//        registration.interceptors(new EasyChannelInterceptor());
//    }

}
