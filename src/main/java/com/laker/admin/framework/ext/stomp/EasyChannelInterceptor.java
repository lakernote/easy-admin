package com.laker.admin.framework.ext.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * websocket消息监听，用于监听websocket用户连接情况
 */
@Slf4j
@Component
public class EasyChannelInterceptor implements ChannelInterceptor {


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 在消息实际发送到通道之前调用。这允许在必要时修改消息。如果此方法返回null，则实际的发送调用不会发生。
     *
     * @param message
     * @param messageChannel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        return message;
    }

    /**
     * 在消息发送后立刻调用，boolean值参数表示该调用的返回值
     *
     * @param message
     * @param messageChannel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel messageChannel, boolean sent) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        EasyPrincipal user = (EasyPrincipal) accessor.getUser();
        // 这里只是单纯的打印，可以根据项目的实际情况做业务处理
        if (user != null) {
            log.info("postSend 中获取httpSession key：" + user.getName());
        }
        // 忽略心跳消息等非STOMP消息
        if (accessor.getCommand() == null) {
            return;
        }

        // 根据连接状态做处理，这里也只是打印了下，可以根据实际场景，对上线，下线，首次成功连接做处理
        log.info(accessor.getCommand().name());
        switch (accessor.getCommand()) {
            // 首次连接
            case CONNECT:
                log.info("httpSession key：" + user.getName() + " 首次连接");
                messageChannel.send(message);
                break;
            // 连接中
            case CONNECTED:
                break;
            // 下线
            case DISCONNECT:
                log.info("httpSession key：" + user.getName() + " 下线");
                break;
            default:
                break;
        }


    }

    /**
     * 1. 在消息发送完成后调用，而不管消息发送是否产生异常，在次方法中，我们可以做一些资源释放清理的工作
     * 2. 此方法的触发必须是preSend方法执行成功，且返回值不为null,发生了实际的消息推送，才会触发
     *
     * @param message
     * @param messageChannel
     * @param b
     * @param e
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {
        log.info("afterSendCompletion");
    }

    /**
     * 1. 在消息被实际检索之前调用，如果返回false,则不会对检索任何消息，只适用于(PollableChannels)，
     * 2. 在websocket的场景中用不到
     *
     * @param messageChannel
     * @return
     */
    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        log.info("preReceive");
        return true;
    }

    /**
     * 1. 在检索到消息之后，返回调用方之前调用，可以进行信息修改，如果返回null,就不会进行下一步操作
     * 2. 适用于PollableChannels，轮询场景
     *
     * @param message
     * @param messageChannel
     * @return
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        log.info("postReceive");
        return message;
    }

    /**
     * 1. 在消息接收完成之后调用，不管发生什么异常，可以用于消息发送后的资源清理
     * 2. 只有当preReceive 执行成功，并返回true才会调用此方法
     * 2. 适用于PollableChannels，轮询场景
     *
     * @param message
     * @param messageChannel
     * @param e
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

        log.info("afterReceiveCompletion");
    }
}
