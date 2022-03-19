package com.laker.admin.framework.aop.repeatedsubmit;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LimitSubmitAspect {
    LFUCache<Object, Object> LFUCACHE = CacheUtil.newLFUCache(100, 60 * 1000);

    @Pointcut("@annotation(RepeatSubmitLimit)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object handleSubmit(ProceedingJoinPoint joinPoint) throws Throwable {


        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解信息
        RepeatSubmitLimit repeatSubmitLimit = method.getAnnotation(RepeatSubmitLimit.class);
        int limitTime = repeatSubmitLimit.time();
        String key = getLockKey(joinPoint, repeatSubmitLimit);
        Object result = LFUCACHE.get(key, false);
        if (result != null) {
            throw new BusinessException("请勿重复访问！");
        }
        LFUCACHE.put(key, StpUtil.getLoginId(), limitTime * 1000);
        try {
            Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
            throw e;
        } finally {
            LFUCACHE.remove(key);
        }
    }

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    private String getLockKey(ProceedingJoinPoint joinPoint, RepeatSubmitLimit repeatSubmitLimit) {
        String businessKey = repeatSubmitLimit.businessKey();
        boolean userLimit = repeatSubmitLimit.userLimit();
        String businessParam = repeatSubmitLimit.businessParam();
        if (userLimit) {
            businessKey = businessKey + ":" + StpUtil.getLoginId();
        }

        if (StrUtil.isNotBlank(businessParam)) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            EvaluationContext context = new MethodBasedEvaluationContext(null, method, joinPoint.getArgs(), NAME_DISCOVERER);
            String key = PARSER.parseExpression(businessParam).getValue(context, String.class);
            businessKey = businessKey + ":" + key;
        }
        return businessKey;
    }
}