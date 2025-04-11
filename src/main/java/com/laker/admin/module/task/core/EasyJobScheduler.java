package com.laker.admin.module.task.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.lock.IEasyLocker;
import com.laker.admin.framework.lock.base.EasyLocker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SchedulingConfigurer 对定时任务的调度进行定制
 * SchedulingConfigurer 接口可以用来配置定时任务的调度器
 * 需要开启@EnableScheduling
 * ScheduledTaskRegistrar 用于注册定时任务，支持多种定时任务的配置方式
 */
@Slf4j
@Component
@EnableScheduling
public class EasyJobScheduler implements SchedulingConfigurer {

    private final ApplicationContext applicationContext;
    private ScheduledTaskRegistrar taskRegistrar;
    private final Map<String, IEasyJob> taskMap = new HashMap<>();
    private final Map<String, List<String>> parentTaskMap = new HashMap<>();

    @Autowired
    private IEasyLocker easyLocker;

    public EasyJobScheduler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void init() {
        final List<Method> annotationMethods = findAnnotationMethods(Scheduled.class);
        if (CollUtil.isNotEmpty(annotationMethods)) {
            log.warn("定时任务使用了@Scheduled注解，请使用@EasyJob注解替换");
            for (Method method : annotationMethods) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);
                log.warn("定时任务方法: {} {} {}", method.getDeclaringClass().getName(), method.getName(), scheduled);
            }
            throw new RuntimeException("定时任务使用了@Scheduled注解，请使用@EasyJob注解替换");
        }
        Map<String, IEasyJob> beansOfType = applicationContext.getBeansOfType(IEasyJob.class);
        if (MapUtil.isEmpty(beansOfType)) {
            log.warn("未查询到IJob实现类");
            return;
        }
        // 遍历加入到任务执行器中
        beansOfType.forEach((s, job) -> {
            EasyJob easyJobAnnotation = AnnotationUtils.findAnnotation(job.getClass(), EasyJob.class);
            if (easyJobAnnotation != null) {
                // 放入任务列表
                taskMap.put(easyJobAnnotation.taskCode(), job);
                // 处理父子任务关系 key为父任务，value为子任务列表
                if (!"".equals(easyJobAnnotation.parentTaskCode())) {
                    parentTaskMap.computeIfAbsent(easyJobAnnotation.parentTaskCode(), k -> new ArrayList<>()).add(easyJobAnnotation.taskCode());
                }
                scheduleJob(easyJobAnnotation, job);
            }
        });

    }

    private void scheduleJob(EasyJob easyJob, IEasyJob job) {
        log.info("注册定时任务: {} {}", easyJob.taskCode(), easyJob.taskName());
        Runnable task = () -> {
            // TODO 分布式场景下，只能有一个节点执行或者多个节点执行，执行不同的任务
            MDC.put(EasyAdminConstants.TRACE_ID, IdUtil.simpleUUID());
            EasyLocker lock = null;
            try {
                lock = easyLocker.tryAcquire(easyJob.taskCode(), Duration.ofSeconds(easyJob.timeout()));
                if (lock == null) {
                    log.warn("定时任务 {} 被锁定，跳过本次执行", easyJob.taskCode());
                    return;
                }
                // 统计任务执行时间，如果超过了超时时间，则记录日志
                long startTime = System.nanoTime();
                job.execute(null);
                triggerSubTasks(easyJob.taskCode());
                long endTime = System.nanoTime();
                long duration = Duration.ofNanos(endTime - startTime).toSeconds();
                if (duration > easyJob.timeout()) {
                    log.warn("定时任务 {} 执行超时，耗时 {} 秒", easyJob.taskCode(), duration);
                } else {
                    log.info("定时任务 {} 执行完成，耗时 {} 秒", easyJob.taskCode(), duration);
                }
            } catch (Exception e) {
                log.error("定时任务执行失败: {} {}", easyJob.taskCode(), easyJob.taskName(), e);
            } finally {
                if (lock != null) {
                    easyLocker.release(lock);
                }
                MDC.remove(EasyAdminConstants.TRACE_ID);
            }
        };
        if (!"".equals(easyJob.cron())) {
//            taskRegistrar.addCronTask(new CronTask(task, easyJob.cron()));
//            taskRegistrar.addTriggerTask(task, new CronTrigger(easyJob.cron()));
            taskRegistrar.addTriggerTask(task, triggerContext -> {
                // TODO 实现个分布式令牌 例如针对一个key，多少时间内能获取到指定数量的令牌
                Instant instant = new CronTrigger(easyJob.cron()).nextExecution(triggerContext);
                if (instant == null) {
                    log.warn("定时任务 {} 的执行时间为 null, 即不会执行", easyJob.taskCode());
                }
                return instant;
            });
        } else if (easyJob.fixedRate() > 0) {
//            taskRegistrar.addFixedRateTask(new FixedRateTask(task, Duration.ofSeconds(easyJob.fixedRate()), Duration.ZERO));
//            taskRegistrar.addTriggerTask(task, triggerContext -> {
//                return new PeriodicTrigger(Duration.ofSeconds(easyJob.fixedRate())).nextExecution(triggerContext);
//            });
            taskRegistrar.addTriggerTask(task, triggerContext -> {
                // 这里可以根据需要自定义触发器
                // 任务的最后安排的执行时间，如果之前没有安排则返回 null。
                Instant lastExecution = triggerContext.lastScheduledExecution();
                // 任务的最后完成时间，如果之前没有安排，则返回 null。
                Instant lastCompletion = triggerContext.lastCompletion();
                if (lastExecution == null || lastCompletion == null) {
                    return triggerContext.getClock().instant();
                }
                final Instant instant = lastExecution.plus(Duration.ofSeconds(easyJob.fixedRate()));
                return instant;
            });
        } else if (easyJob.fixedDelay() > 0) {
            taskRegistrar.addFixedDelayTask(new FixedDelayTask(task, Duration.ofSeconds(easyJob.fixedDelay()), Duration.ZERO));
        } else {
//            taskRegistrar.addTriggerTask(task, triggerContext -> {
//                // 这里可以根据需要自定义触发器
//                Instant lastExecution = triggerContext.lastScheduledExecution();
//                Instant lastCompletion = triggerContext.lastCompletion();
//                if (lastExecution == null || lastCompletion == null) {
//                    Instant instant = triggerContext.getClock().instant();
//                    return instant;
//                }
//                return lastCompletion.plus(Duration.ofSeconds(easyJob.fixedDelay()));
//            });
            log.warn("任务 {} 没有配置定时策略", easyJob.taskCode());
        }
    }

    private void triggerSubTasks(String parentTaskCode) throws Exception {
        List<String> subTaskCodes = parentTaskMap.get(parentTaskCode);
        if (subTaskCodes != null) {
            for (String subTaskCode : subTaskCodes) {
                IEasyJob subJob = taskMap.get(subTaskCode);
                if (subJob != null) {
                    subJob.execute(null);
                }
            }
        }
    }

    private List<Method> findAnnotationMethods(Class<? extends Annotation> annotationClass) {
        List<Method> scheduledMethods = new ArrayList<>();
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(annotationClass)) {
                    scheduledMethods.add(method);
                }
            }
        }
        return scheduledMethods;
    }

    /**
     * taskRegistrar 是一个ScheduledTaskRegistrar对象，用于注册定时任务
     * 它提供了多种方法来添加定时任务，包括使用cron表达式、固定延迟和固定速率等方式
     *
     * @param taskRegistrar the registrar to be configured
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler();// 配置自定义线程池
        this.taskRegistrar = taskRegistrar;
        init();
    }
}