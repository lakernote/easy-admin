package com.laker.admin.framework.ext.interceptor;

import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * @author laker
 */
public class EasyTraceAnnotationInterceptor implements HandlerInterceptor {

    private final long time;

    public EasyTraceAnnotationInterceptor(long time) {
        this.time = time;
    }

    /**
     * 每次请求之前触发的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 获取处理method
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        TraceContext.addSpan(method.getDeclaringClass().getSimpleName() + "|" + method.getName(), SpanType.Controller);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 获取处理method
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
        TraceContext.stopSpan(time);
    }
}
