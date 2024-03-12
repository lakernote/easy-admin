package com.laker.admin.framework.ext.interceptor;

import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author laker
 */
public class TraceAnnotationInterceptor implements HandlerInterceptor {


    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 获取处理method
        if (handler instanceof HandlerMethod == false) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        TraceContext.addSpan(method.getDeclaringClass().getSimpleName() + "|" + method.getName(), SpanType.Controller);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 获取处理method
        if (handler instanceof HandlerMethod == false) {
            return;
        }
        TraceContext.stopSpan(1000);
    }
}
