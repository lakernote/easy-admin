package com.laker.admin.module.chat;

import com.laker.admin.framework.ext.stomp.EasyChatMessage;
import com.laker.admin.framework.ext.stomp.EasyPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketCharConroller {
    @Autowired
    StompMessageService stompMessageService;

    /**
     * 聊天室聊天
     */
    @MessageMapping(StompConstant.PUB_CHAT_ROOM)
    public void chatRoom(EasyChatMessage chatMessage, EasyPrincipal user) throws Exception {
        stompMessageService.sendMessage(StompConstant.SUB_CHAT_ROOM, chatMessage);
    }

    /**
     * 私聊聊天
     *       // 本地订阅
     *         stompClient.subscribe('/user/' + uid + '/chat', function (data) {
     *             handleMessage(getData(data.body));
     *         });
     * @param user 发送消息的用户对象
     * @throws Exception
     */
    @MessageMapping(StompConstant.PUB_USER)
    public void sendToUser(EasyChatMessage chatMessage, EasyPrincipal user) throws Exception {
        stompMessageService.sendMessageToUser(chatMessage, chatMessage.getTo());
    }

    /**
     * 消息异常处理
     *
     * @param e    异常对象
     * @param user 发送消息的用户对象
     */
    @MessageExceptionHandler(Exception.class)
    public void handleExceptions(Exception e, EasyPrincipal user) {

        log.error("error:", e);

    }
}
