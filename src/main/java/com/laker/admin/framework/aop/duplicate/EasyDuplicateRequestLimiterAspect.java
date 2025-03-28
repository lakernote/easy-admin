package com.laker.admin.framework.aop.duplicate;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.laker.admin.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 仅用于防止重复提交/防并发，不保证幂等性
 * <pre>
 * 防止重复提交，是指用户在提交表单时，由于网络延迟、用户重复点击等原因，导致多次提交表单的情况。是短期内重复提交的问题。
 * 幂等性，是指同一个请求，无论调用多少次，结果都是一样的。是长期内重复提交的问题。
 * 防止重复提交，不保证幂等性。但是幂等性一定保证了防止重复提交。
 * 1 、前端防抖，按钮点击后立即禁用，等接口返回后再视情况启用或跳转页面。
 * 2 、后端限流，同一来源相同参数在一定时间间隔内只允许调用一次。这个方案的好处是通用，且可以顺便减轻接口被非法调用的压力。
 * 3 、使用令牌，前端提交数据前先获取一个令牌，后端限制令牌只能使用一次。
 * </pre>
 * <a href="https://blog.csdn.net/abu935009066/article/details/117471885">...</a>
 *
 * @author laker
 */
@Component
@Aspect
@Slf4j
public class EasyDuplicateRequestLimiterAspect {
    private final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private final ExpressionParser PARSER = new SpelExpressionParser();
    private final DuplicateRequestLimiter duplicateRequestLimiter;

    public EasyDuplicateRequestLimiterAspect(DuplicateRequestLimiter duplicateRequestLimiter) {
        this.duplicateRequestLimiter = duplicateRequestLimiter;
    }

    /**
     * <pre>
     * 获取注解参数的方式：
     * 方式1： 直接使用注解参数
     * 方式2： 使用method.getAnnotation(EasyRepeatSubmitLimit.class)
     *
     * "@annotation"用于匹配那些带有指定注解的方法。也就是说，当 某个方法被指定的注解标记时，该方法就会成为切入点的一部分。
     * "@within"用于匹配那些所在类带有指定注解的所有方法。只要 类被指定的注解标记，该类中的所有方法都会成为切入点的一部分。
     * " @annotation 关注的是方法上的注解，只有被注解标记的方法才会被匹配。
     * " @within 关注的是类上的注解，只要类被注解标记，该类中的所有方法都会被匹配。
     * </pre>
     */
    @Around("@annotation(easyDuplicateRequestLimiterParam)")
    public Object handleSubmit(ProceedingJoinPoint joinPoint,
                               EasyDuplicateRequestLimiter easyDuplicateRequestLimiterParam) throws Throwable {
        // 1.获取类上的注解 和 方法上的注解，方法上的注解优先级高
        final EasyDuplicateRequestLimiter easyDuplicateRequestLimiter = getDuplicateRequestLimiter(joinPoint);
        // 2.获取注解参数
        int timeout = easyDuplicateRequestLimiter.timeout();
        // 3.获取重复提交key
        String key = getDuplicateRequestKey(joinPoint, easyDuplicateRequestLimiter);
        // 4.限流
        if (!duplicateRequestLimiter.tryRequest(key, timeout)) {
            throw new BusinessException("请勿重复访问！");
        }
        return joinPoint.proceed();
    }

    /**
     * 获取类上的注解 和 方法上的注解，方法上的注解优先级高
     *
     * @param joinPoint 切点
     * @return EasyDuplicateRequestLimiter
     */
    private static EasyDuplicateRequestLimiter getDuplicateRequestLimiter(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        // 获取方法
        Method method = ((MethodSignature) signature).getMethod();
        // 获取方法上的注解
        EasyDuplicateRequestLimiter easyDuplicateRequestLimiter = method.getAnnotation(EasyDuplicateRequestLimiter.class);
        // 如果方法上的注解不存在，则获取类上的注解
        if (easyDuplicateRequestLimiter == null) {
            easyDuplicateRequestLimiter = method.getDeclaringClass().getAnnotation(EasyDuplicateRequestLimiter.class);
        }
        // 如果还是没有找到注解，则抛出异常
        if (easyDuplicateRequestLimiter == null) {
            throw new BusinessException("Annotation EasyDuplicateRequestLimiter not found on method or class.");
        }
        return easyDuplicateRequestLimiter;
    }

    /**
     * 获取重复提交key
     * example: businessKey:userId?:businessParam
     *
     * @param joinPoint                   切点
     * @param easyDuplicateRequestLimiter 注解
     * @return String
     */
    private String getDuplicateRequestKey(ProceedingJoinPoint joinPoint, EasyDuplicateRequestLimiter easyDuplicateRequestLimiter) {
        String businessKey = easyDuplicateRequestLimiter.businessKey();
        boolean userLimit = easyDuplicateRequestLimiter.userLimit();
        String businessParam = easyDuplicateRequestLimiter.businessParam();
        if (userLimit) {
            businessKey = businessKey + ":" + StpUtil.getLoginId();
        }

        if (CharSequenceUtil.isNotBlank(businessParam)) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            // 创建SpEL表达式解析器
            EvaluationContext context = new MethodBasedEvaluationContext(null, method, joinPoint.getArgs(), NAME_DISCOVERER);
            String key = PARSER.parseExpression(businessParam).getValue(context, String.class);
            key = key == null ? "" : key.trim();
            businessKey = businessKey + ":" + key;
        }
        return businessKey;
    }
}