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

    /**
     * <p>
     * execution 方法描述匹配
     * 第一个通配符匹配任何返回值
     * com.laker..*
     * 匹配com.laker包或子包中的任何类型
     * 最后一个匹配任何方法名称
     * (..)匹配任意数量的参数（零个或多个）
     * </p>
     * 任何目录中包含 remote的包
     */
    @Pointcut("execution(public * com.laker..remote..*(..))")
    public void remoteAspect() {
    }


    /**
     * 拦截【方法】上有@LakerTrace注解
     */
    @Pointcut("@annotation(com.laker.admin.framework.aop.trace.LakerTrace)")
    public void annotationAspect() {
    }

    /**
     * 拦截【类】上有@LakerTrace注解
     */
    @Pointcut("@within(com.laker.admin.framework.aop.trace.LakerTrace)")
    public void withinAspect() {
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

    //@Around("controllerAspect() || serviceAspect() ||  mapperAspect() || remoteAspect()")
    @Around("withinAspect() || remoteAspect()")
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
