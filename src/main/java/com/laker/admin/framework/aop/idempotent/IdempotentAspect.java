package com.laker.admin.framework.aop.idempotent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class IdempotentAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private IdempotentHandler idempotentService;

    @Around("@annotation(Idempotent)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        String key = parseSpelKey(joinPoint, idempotent.key());
        long expireTime = idempotent.expireTime();
        if (idempotentService.checkAndSet(key, expireTime)) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                // 若方法执行过程中出现异常，可选择移除幂等键，以便后续重试
                idempotentService.remove(key);
                throw e;
            }
        } else {
            throw new RuntimeException(idempotent.message());
        }
    }

    private String parseSpelKey(ProceedingJoinPoint joinPoint, String spelExpression) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return parser.parseExpression(spelExpression).getValue(context, String.class);
    }
}    