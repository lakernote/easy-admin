package com.laker.admin.framework.aop.trace;

import org.apache.ibatis.annotations.Mapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * @author laker
 */
public class TraceUtils {

    public static final String CONTROLLER = "controller";
    public static final String SERVICE = "service";
    public static final String MAPPER = "mapper";
    public static final String REMOTE = "remote";

    public static SpanType getSpanType(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getName().toLowerCase();
        Class<?> targetClass = pjp.getTarget().getClass();
        LakerTrace methodViewTrace = AnnotationUtils.findAnnotation(method, LakerTrace.class);
        if (methodViewTrace != null) {
            return methodViewTrace.spanType();
        }
        LakerTrace classViewTrace = AnnotationUtils.findAnnotation(targetClass, LakerTrace.class);
        if (classViewTrace != null) {
            return classViewTrace.spanType();
        }

        if (AnnotationUtils.findAnnotation(targetClass, Controller.class) != null || AnnotationUtils.findAnnotation(targetClass, RestController.class) != null) {
            return SpanType.Controller;
        } else if (AnnotationUtils.findAnnotation(targetClass, Service.class) != null) {
            return SpanType.Service;
        } else if (AnnotationUtils.findAnnotation(targetClass, Mapper.class) != null) {
            return SpanType.Mapper;
        }
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

}
