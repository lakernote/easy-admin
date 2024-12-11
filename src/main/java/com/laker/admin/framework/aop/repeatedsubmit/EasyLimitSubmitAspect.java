package com.laker.admin.framework.aop.repeatedsubmit;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.laker.admin.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
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
 * <p>
 * 1 、前端防抖，按钮点击后立即禁用，等接口返回后再视情况启用或跳转页面。
 * 2 、后端限流，同一来源在一定时间间隔内只允许调用一次。这个方案的好处是通用，且可以顺便减轻接口被非法调用的压力。
 * 3 、使用令牌，前端提交数据前先获取一个令牌，后端限制令牌只能使用一次。
 * </p>
 * <a href="https://blog.csdn.net/abu935009066/article/details/117471885">...</a>
 *
 * @author laker
 */
@Component
@Aspect
@Slf4j
public class EasyLimitSubmitAspect {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    final EasyRepeatSubmiter easyRepeatSubmiter;

    public EasyLimitSubmitAspect(EasyRepeatSubmiter easyRepeatSubmiter) {
        this.easyRepeatSubmiter = easyRepeatSubmiter;
    }

    /**
     * 获取 注解有2中方式
     * 方式1： easyRepeatSubmitLimitParam
     * 方式2： method.getAnnotation(EasyRepeatSubmitLimit.class)
     *
     * @param joinPoint                  切点
     * @param easyRepeatSubmitLimitParam 注解
     * @return Object
     * @throws Throwable 异常
     */
    @Around("@annotation(easyRepeatSubmitLimitParam)")
    public Object handleSubmit(ProceedingJoinPoint joinPoint, EasyRepeatSubmitLimit easyRepeatSubmitLimitParam) throws Throwable {
        // 获取方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取注解
        EasyRepeatSubmitLimit easyRepeatSubmitLimit = method.getAnnotation(EasyRepeatSubmitLimit.class);
        int timeout = easyRepeatSubmitLimit.timeout();
        String key = getLockKey(joinPoint, easyRepeatSubmitLimit);
        if (!easyRepeatSubmiter.tryAcquire(key, timeout)) {
            throw new BusinessException("请勿重复访问！");
        }
        return joinPoint.proceed();
    }

    private String getLockKey(ProceedingJoinPoint joinPoint, EasyRepeatSubmitLimit easyRepeatSubmitLimit) {
        String businessKey = easyRepeatSubmitLimit.businessKey();
        boolean userLimit = easyRepeatSubmitLimit.userLimit();
        String businessParam = easyRepeatSubmitLimit.businessParam();
        if (userLimit) {
            businessKey = businessKey + ":" + StpUtil.getLoginId();
        }

        if (CharSequenceUtil.isNotBlank(businessParam)) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            // 创建SpEL表达式解析器
            EvaluationContext context = new MethodBasedEvaluationContext(null, method, joinPoint.getArgs(), NAME_DISCOVERER);
            String key = PARSER.parseExpression(businessParam).getValue(context, String.class);
            businessKey = businessKey + ":" + key;
        }
        return businessKey;
    }
}