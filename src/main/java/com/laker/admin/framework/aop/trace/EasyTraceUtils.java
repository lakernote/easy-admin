package com.laker.admin.framework.aop.trace;

import org.apache.ibatis.annotations.Mapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author laker
 */
public class EasyTraceUtils {

    public static final String CONTROLLER = "controller";
    public static final String SERVICE = "service";
    public static final String MAPPER = "mapper";
    public static final String REMOTE = "remote";

    private EasyTraceUtils() {
        // 私有构造器
    }

    public static SpanType getSpanType(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getSimpleName().toLowerCase();
        Class<?> targetClass = pjp.getTarget().getClass();
        // 1.优先级1 先根据类上的注解判断是什么类型的服务
        if (AnnotationUtils.findAnnotation(targetClass, Controller.class) != null || AnnotationUtils.findAnnotation(targetClass, RestController.class) != null) {
            return SpanType.Controller;
        }
        // 1.1 service先看有没有EasyTrace注解，有：用，无：用service
        if (AnnotationUtils.findAnnotation(targetClass, Service.class) != null) {
            SpanType spanType = getSpanTypeFromEasyTrace(method, targetClass);
            return Objects.requireNonNullElse(spanType, SpanType.Service);
        }
        if (AnnotationUtils.findAnnotation(targetClass, Mapper.class) != null) {
            return SpanType.Mapper;
        }
        // 2.优先级2 使用EasyTrace
        SpanType spanType = getSpanTypeFromEasyTrace(method, targetClass);
        if (spanType != null) {
            return spanType;
        }
        // 3.优先级3 使用类名称是否包含关键字
        if (className.contains(CONTROLLER)) {
            return SpanType.Controller;
        } else if (className.contains(SERVICE)) {
            return SpanType.Service;
        } else if (className.contains(MAPPER)) {
            return SpanType.Mapper;
        } else if (className.contains(REMOTE)) {
            return SpanType.Remote;
        } else {
            return SpanType.Others;
        }

    }

    private static SpanType getSpanTypeFromEasyTrace(Method method, Class<?> targetClass) {
        EasyTrace methodViewTrace = AnnotationUtils.findAnnotation(method, EasyTrace.class);
        if (methodViewTrace != null) {
            return methodViewTrace.spanType();
        }
        EasyTrace classViewTrace = AnnotationUtils.findAnnotation(targetClass, EasyTrace.class);
        if (classViewTrace != null) {
            return classViewTrace.spanType();
        }
        return null;
    }

}
