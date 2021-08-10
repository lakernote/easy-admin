package com.laker.admin.framework.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laker.admin.utils.http.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * Bean的优先级设置为最高
 */
@Aspect
//@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MetricsAspect {
    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void withAnnotationRestController() {
    }

    @Around("withAnnotationRestController()")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.toShortString();
        Object returnValue;
        Instant start = Instant.now();
        try {
            returnValue = pjp.proceed();
        } catch (Exception ex) {
            log.info("method:{},fail,cost:{}ms,uri:{},param:{}", name, Duration.between(start, Instant.now()).toMillis(), HttpServletRequestUtil.getRequestURI(), objectMapper.writeValueAsString(pjp.getArgs()));
            log.error(name, ex);
            throw ex;

        }
        log.info("method:{},success,cost:{}ms,uri:{},param:{},return:{}", name, Duration.between(start, Instant.now()).toMillis(), HttpServletRequestUtil.getRequestURI(), objectMapper.writeValueAsString(pjp.getArgs()), objectMapper.writeValueAsString(returnValue));
        return returnValue;
    }
}
