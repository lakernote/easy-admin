package com.laker.admin.module.chat;

public interface StompConstant {
    /**
     * 广播式
     */
    String STOMP_TOPIC = "/topic";
    /**
     * 一对一式
     */
    String STOMP_USER = "/user";
    /**
     * 单用户消息订阅地址
     */
    String SUB_USER = "/chat";
    /**
     * 私聊聊天
     * 目的地是“/app/chat”。（“/app”前缀是隐含 的，因为我们将其配置为应用的目的地前缀）
     */
    String PUB_USER = "/chat";
    /**
     * 聊天室聊天
     * 目的地是“/app/chatRoom”。（“/app”前缀是隐含 的，因为我们将其配置为应用的目的地前缀）
     */
    String PUB_CHAT_ROOM = "/chatRoom";

    /**
     * 聊天室消息订阅地址
     */
    String SUB_CHAT_ROOM = "/topic/chatRoom";
    /**
     * 异常消息订阅地址
     */
    String SUB_ERROR = "/error";
    /**
     * 用户上下线状态消息订阅地址
     */
    String SUB_STATUS = "/topic/status";
    /**
     * 聊天室消息撤消
     */
    String PUB_CHAT_ROOM_REVOKE = "/chatRoom/revoke";
}
