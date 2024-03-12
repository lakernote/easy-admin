package com.laker.admin.framework.ext.websocket;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

public class LakerSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    // 拦截器返回false，则不会进行websocket协议的转换
    // 可以获取请求参数做认证鉴权
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        // 将所有查询参数复制到 WebSocketSession 属性映射
        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            // 放入的值可以从WebSocketHandler里面的WebSocketSession拿出来
            attributes.put(parameterName, servletRequest.getParameter(parameterName));
        }
        if (servletRequest.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, "");
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private void setErrorResponse(ServerHttpResponse response, HttpStatus status, String errorMessage) {
        response.setStatusCode(status);
        if (errorMessage != null) {
//            try {
//                objectMapper.writeValue(response.getBody(), new ErrorDetail(status.value(), errorMessage));
//            } catch (IOException ioe) {
//            }
        }
    }
}
