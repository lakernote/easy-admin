package com.laker.admin.framework.ext.websocket;

import com.laker.admin.framework.ext.actuator.metrics.websocket.WebsocketMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laker
 */
@Service
@Slf4j
public class LakerChatHandler extends AbstractWebSocketHandler {
    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
    WebsocketMetrics websocketMetrics;

    public LakerChatHandler(WebsocketMetrics websocketMetrics) {
        this.websocketMetrics = websocketMetrics;
        websocketMetrics.websocketGauge(webSocketSessionMap);
    }

    /**
     * 成功连接时
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionMap.put(session.getId(), new ConcurrentWebSocketSessionDecorator(session, 10 * 1000, 64000));
        websocketMetrics.websocketOpenCounter();
    }

    /**
     * 处理textmessage
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String exception = "";
        long start = System.nanoTime();
        try {
            // 有消息就广播下
            for (Map.Entry<String, WebSocketSession> entry : webSocketSessionMap.entrySet()) {
                String s = entry.getKey();
                WebSocketSession webSocketSession = entry.getValue();
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(s + ":" + message.getPayload()));
                    log.info("send to {} msg:{}", s, message.getPayload());
                }
            }
        } catch (Exception e) {
            exception = e.getClass().getSimpleName();
            throw e;
        } finally {
            websocketMetrics.websocketProcessTimer(System.nanoTime() - start, exception);
        }
    }


    /**
     * 报错时
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("handleTransportError:: sessionId: {} {}", session.getId(), exception.getMessage(), exception);
        if (session.isOpen()) {
            try {
                session.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    /**
     * 连接关闭时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        websocketMetrics.webSocketCloseConnectionCounter(1, status.getCode());
        WebSocketSession concurrentSession = webSocketSessionMap.remove(session.getId());
        if (concurrentSession.isOpen()) {
            try {
                concurrentSession.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    //处理binarymessage
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
    }

    //处理pong
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    //是否支持报文拆包
    // 决定是否接受半包，因为websocket协议比较底层，好像Tcp协议一样，如果发送大消息可能会拆成多个小报文。如果不希望处理不完整的报文，希望底层帮忙聚合成完整消息将此方法返回false,这样底层会等待完整报文到达聚合后才回调。
    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }
}
