package com.laker.admin.framework.aop.idempotent;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class IdempotentAspect {

    private final ExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private IdempotentHandler idempotentService;

    /**
     * 环绕通知
     *
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(Idempotent)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();

        // 判断当前是否在事务中，如果不在事务中，抛出异常并记录日志
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            log.error("方法 {} 未在事务中执行，幂等操作要求在事务内进行", methodName);
            throw new IllegalStateException("方法 " + methodName + " 必须在事务中执行");
        }

        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        String key = null;
        try {
            key = parseSpelKey(joinPoint, idempotent.key());
        } catch (Exception e) {
            log.error("解析幂等键的 SpEL 表达式失败，方法: {}", methodName, e);
            throw new RuntimeException("解析幂等键失败", e);
        }


        long expireTime = idempotent.expireTime();
        boolean isIdempotentSet = false;
        try {
            isIdempotentSet = idempotentService.checkAndSet(key, expireTime);
            if (isIdempotentSet) {
                log.info("幂等键 {} 设置成功，开始执行方法 {}", key, methodName);
                Object result = joinPoint.proceed();
                log.info("方法 {} 执行成功，幂等操作完成", methodName);
                return result;
            } else {
                log.info("幂等键 {} 已存在，方法 {} 重复调用，返回错误信息: {}", key, methodName, idempotent.message());
                throw new RuntimeException(idempotent.message());
            }
        } catch (Exception e) {
            if (isIdempotentSet) {
                try {
                    idempotentService.remove(key);
                    log.info("方法 {} 执行异常，已移除幂等键 {}", methodName, key);
                } catch (Exception removeException) {
                    log.error("移除幂等键 {} 失败，方法: {}", key, methodName, removeException);
                }
            }
            log.error("方法 {} 执行过程中出现异常", methodName, e);
            throw e;
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