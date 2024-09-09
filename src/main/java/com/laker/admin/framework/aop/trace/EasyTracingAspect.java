package com.laker.admin.framework.aop.trace;

import com.laker.admin.config.EasyAdminConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author laker
 */
@Component
@Slf4j
@Aspect
public class EasyTracingAspect {

    private final EasyAdminConfig easyAdminConfig;

    public EasyTracingAspect(EasyAdminConfig easyAdminConfig) {
        this.easyAdminConfig = easyAdminConfig;
    }

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
        // do nothing
    }


    /**
     * 拦截【方法】上有@EasyTrace注解
     */
    @Pointcut("@annotation(com.laker.admin.framework.aop.trace.EasyTrace)")
    public void annotationAspect() {
        // do nothing
    }

    /**
     * 拦截【类】上有@EasyTrace注解
     */
    @Pointcut("@within(com.laker.admin.framework.aop.trace.EasyTrace)")
    public void withinAspect() {
        // do nothing
    }

    /**
     * 拦截【方法】上有@EasyIgnoreTrace的注解
     * 使用场景
     * - @EasyTrace注解在类上，或者包扫描到了整个类，但是其中的某个方法不想拦截
     * - 在方法上注解@EasyIgnoreTrace即可
     */
    @Pointcut("!@annotation(com.laker.admin.framework.aop.trace.EasyIgnoreTrace)")
    public void annotationIgnoreAspect() {
        // do nothing
    }

    /**
     * 拦截mapper包下的所有方法
     */
    @Pointcut("execution(* com.laker..mapper.*.*(..))")
    public void mapperAspect() {
        // do nothing
    }

    /**
     * 拦截service包下的所有方法
     */
    @Pointcut("execution(public * com.laker..service.*.*(..)) || execution(public * com.laker..*Service.*(..))")
    public void serviceAspect() {
        // do nothing
    }

    /**
     * 拦截controller包下的所有方法
     */
    @Pointcut("execution(public * com.laker..controller.*.*(..)) || execution(public * com.laker..*Controller.*(..))")
    public void controllerAspect() {
        // do nothing
    }

    /**
     * 处理
     *
     * @param pjp 切入点
     * @return Object
     * @throws Throwable 异常
     */

    @Around("(withinAspect() || annotationAspect()) && annotationIgnoreAspect()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        Object obj;
        TraceContext.addSpan(pjp);
        try {
            obj = pjp.proceed();
        } finally {
            TraceContext.stopSpan(easyAdminConfig.getTrace().getTime());
        }
        return obj;
    }

}
