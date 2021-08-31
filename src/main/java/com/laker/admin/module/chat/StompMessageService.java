package com.laker.admin.module.chat;

import com.laker.admin.framework.ext.stomp.EasyChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class StompMessageService {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String destination, EasyChatMessage message) {
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    public void sendMessageToUser(EasyChatMessage message, String... receiver) {
        for (int i = 0, len = receiver.length; i < len; i++) {
            // 将消息发送到指定用户 参数说明：1.消息接收者 2.消息订阅地址 3.消息内容
            // 发给单点某个人，私聊,这里的前缀必须是跟 WebSocketStompConfig registry.setUserDestinationPrefix("/user/")中呼应
            simpMessagingTemplate.convertAndSendToUser(receiver[i], StompConstant.SUB_USER, message);
        }
    }
}
