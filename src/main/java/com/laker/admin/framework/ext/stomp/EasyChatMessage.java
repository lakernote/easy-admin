package com.laker.admin.framework.ext.stomp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EasyChatMessage {
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息发送人
     */
    private String from;
    /**
     * 消息接收人
     */
    private String to;
    /**
     * 消息时间
     */
    private Date createTime;
}