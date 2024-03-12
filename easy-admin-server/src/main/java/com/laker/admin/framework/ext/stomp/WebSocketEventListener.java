package com.laker.admin.framework.ext.stomp;

import cn.hutool.core.thread.ThreadUtil;
import com.laker.admin.module.chat.StompConstant;
import com.laker.admin.module.chat.StompMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * websocket事件监听 websocket消息监听，用于监听websocket用户连接情况
 */
@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    StompMessageService stompMessageService;


    /**
     * 建立连接监听
     *
     * @param sessionConnectedEvent
     */
    @EventListener
    public void handleConnectListener(SessionConnectedEvent sessionConnectedEvent) {
        EasyPrincipal user = (EasyPrincipal) sessionConnectedEvent.getUser();
        log.debug("建立连接 {} -> {}", user, sessionConnectedEvent);
        // 加入在线用户列表

        ThreadUtil.execute(() -> {
            // 缓几秒钟再广播消息防止，接收不到
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 广播上线消息
            stompMessageService.sendMessage(StompConstant.SUB_STATUS, EasyChatMessage.builder()
                    .content("欢迎【" + user.getAddress() + "】老弟体验")
                    .createTime(new Date()).build());
            log.debug("广播消息喽");
        });

    }

    /**
     * 断开连接监听
     *
     * @param sessionDisconnectEvent
     */
    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        EasyPrincipal user = (EasyPrincipal) sessionDisconnectEvent.getUser();
        log.debug("断开连接 -> {}", sessionDisconnectEvent);
        // 剔除在线用户列表
        if (user == null) {
            return;
        }
        // 广播下线消息
        stompMessageService.sendMessage(StompConstant.SUB_STATUS, EasyChatMessage.builder()
                .content("期待【" + user.getAddress() + "】老弟再来")
                .createTime(new Date()).build());
        log.debug("广播消息喽");
    }

}