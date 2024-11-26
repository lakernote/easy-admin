package com.laker.admin.framework.ext.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LogResponseReturnValueHandler implements HandlerMethodReturnValueHandler {


    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        // 在处理返回值之前记录日志
        log.info("Returning from method: {} with value: {}", returnType.getMethod().getName(), returnValue);
    }
}