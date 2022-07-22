package com.laker.admin.framework.aop.trace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;

//@Component
@Slf4j
@Aspect
public class TracingAspect {
    @Value("${tracing.time:200}")
    private long time;

    @Pointcut("execution(public * com.laker..remote.*.*(..))")
    public void remoteAspect() {
    }

    @Pointcut("execution(* com.laker..mapper.*.*(..))")
    public void mapperAspect() {
    }

    @Pointcut("execution(public * com.laker..service.*.*(..)) || execution(public * com.laker..*Service.*(..))")
    public void serviceAspect() {
    }

    @Pointcut("execution(public * com.laker..controller.*.*(..)) || execution(public * com.laker..*Controller.*(..))")
    public void controllerAspect() {
    }

    @Around("controllerAspect() || serviceAspect() ||  mapperAspect() || remoteAspect()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        Object obj;
        TraceContext.addSpan(pjp);
        try {
            obj = pjp.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            TraceContext.stopSpan(time);
        }
        return obj;
    }

}
