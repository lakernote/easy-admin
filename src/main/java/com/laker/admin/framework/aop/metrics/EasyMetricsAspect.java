package com.laker.admin.framework.aop.metrics;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.service.IExtLogService;
import com.laker.admin.utils.http.EasyRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * <pre>
 * Bean的优先级设置为最高
 *  1.用于监控方法执行时间
 *  2.记录日志
 *  3.记录请求参数
 *  4.记录返回值
 *  5.记录请求ip
 *  6.记录请求uri
 *  7.记录请求客户端
 *  8.记录请求用户
 *  9.记录请求时间
 *  10.记录请求状态
 *  11.记录请求耗时
 *  12.记录请求方法
 * </pre>
 */
@Aspect
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EasyMetricsAspect {
    private final ObjectMapper objectMapper;
    private final IExtLogService extLogService;

    public EasyMetricsAspect(ObjectMapper objectMapper, IExtLogService extLogService) {
        this.objectMapper = objectMapper;
        this.extLogService = extLogService;
    }

    /**
     * <pre>
     * 定义切点
     *  "@annotation"用于匹配那些带有指定注解的方法。也就是说，当 某个方法被指定的注解标记时，该方法就会成为切入点的一部分。
     *  "@within"用于匹配那些所在类带有指定注解的所有方法。只要 类被指定的注解标记，该类中的所有方法都会成为切入点的一部分。
     *  </pre>
     */
    @Pointcut("@annotation(com.laker.admin.framework.aop.metrics.EasyMetrics) " +
            "|| @within(com.laker.admin.framework.aop.metrics.EasyMetrics)")
    public void withAnnotationMetrics() {
        // do nothing
    }

    @Around("withAnnotationMetrics()")
    public Object metrics(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.toShortString();
        Object returnValue;
        Instant start = Instant.now();
        ExtLog logBean = new ExtLog();
        logBean.setIp(EasyRequestUtil.getRemoteIP());
        logBean.setUri(EasyRequestUtil.getRequestURI());
        logBean.setUserId(StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null);
        UserAgent userAgent = EasyRequestUtil.getRequestUserAgent();
        if (userAgent != null) {
            String client = userAgent.getOs().getName() + "." + userAgent.getBrowser().getName();
            logBean.setClient(client);
        }
        logBean.setRequest(objectMapper.writeValueAsString(pjp.getArgs()));
        logBean.setMethod(name);
        logBean.setStatus(true);
        try {
            returnValue = pjp.proceed();
        } catch (Exception ex) {
            log.info("method:{},fail,cost:{}ms,uri:{},param:{}", name, Duration.between(start, Instant.now()).toMillis(), EasyRequestUtil.getRequestURI(), objectMapper.writeValueAsString(pjp.getArgs()));
            logBean.setCost((int) Duration.between(start, Instant.now()).toMillis());
            logBean.setCreateTime(LocalDateTime.now());
            logBean.setStatus(false);
            extLogService.save(logBean);
            log.error(name, ex);
            throw ex;
        }
        String response = objectMapper.writeValueAsString(returnValue);
        log.debug("method:{},success,cost:{}ms,uri:{},param:{},return:{}", name, Duration.between(start, Instant.now()).toMillis(), EasyRequestUtil.getRequestURI(), objectMapper.writeValueAsString(pjp.getArgs()), response);
        logBean.setCost((int) Duration.between(start, Instant.now()).toMillis());
        logBean.setCreateTime(LocalDateTime.now());
        if (CharSequenceUtil.isNotBlank(response) && response.length() <= 500) {
            logBean.setResponse(response);
        }
        if (JSONUtil.isTypeJSONObject(response)) {
            Boolean success = JSONUtil.parseObj(response).getBool("success", true);
            logBean.setStatus(success);
        }
        try {
            extLogService.save(logBean);
        } catch (Exception e) {
            log.error("保存日志失败", e);
        }
        return returnValue;
    }
}
