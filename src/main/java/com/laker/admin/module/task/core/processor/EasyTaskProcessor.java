package com.laker.admin.module.task.core.processor;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.framework.utils.SpringUtils;
import com.laker.admin.module.enums.TaskStateEnum;
import com.laker.admin.module.task.core.IJob;
import com.laker.admin.module.task.core.TaskJob;
import com.laker.admin.module.task.core.listener.JobListener;
import com.laker.admin.module.task.core.taskstore.ITaskStore;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author laker
 */
@Component
@Slf4j
public class EasyTaskProcessor {


    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private ITaskStore taskStore;

    @Autowired
    private List<JobListener> callBacks;

    @Autowired
    private ISysTaskService sysTaskService;

    /**
     * 启动后存储任务列表
     */
    private final static LFUCache<String, ScheduledFuture> JVM_RUNNING_TASK = CacheUtil.newLFUCache(1000, TimeUnit.DAYS.toMillis(3));


    /**
     * 将任务加入到JVM执行器中
     *
     * @return
     */
    private void startJVMJob(TaskJob task, Map param) {
        String taskCron = task.getTaskCron();
        // TODO taskCron CHECK
        if (StrUtil.isNotBlank(taskCron) && !StrUtil.equals(taskCron, "-")) {
            ScheduledFuture<?> future = threadPoolTaskScheduler
                    .schedule(() -> {
                                try {
                                    String traceId = MDC.get(EasyAdminConstants.TRACE_ID);
                                    if (StrUtil.isBlank(traceId)) {
                                        traceId = IdUtil.simpleUUID();
                                    }
                                    MDC.put(EasyAdminConstants.TRACE_ID, traceId);

                                    callBacks.forEach(jobListener -> {
                                        jobListener.start(task);
                                    });
                                    String taskClassName = task.getTaskClassName();
                                    Map<String, IJob> beansOfType = SpringUtils.getBeansOfType(IJob.class);
                                    for (IJob iJob : beansOfType.values()) {
                                        if (StrUtil.equals(taskClassName, iJob.getClass().getName())) {
                                            iJob.execute(param);
                                            break;
                                        }
                                    }

                                } catch (Exception e) {
                                    callBacks.forEach(jobListener -> {
                                        jobListener.exception(task, e);
                                    });
                                } finally {
                                    callBacks.forEach(jobListener -> {
                                        jobListener.end(task);
                                    });
                                    MDC.clear();
                                }
                            },
                            new CronTrigger(taskCron));
            JVM_RUNNING_TASK.put(task.getTaskCode(), future);
        } else {
            log.warn("cron表达式为：{}，Job信息：{}，不予启动任务。", taskCron, task);
        }
    }

    private void removeJvmTask(String taskCode) {
        ScheduledFuture scheduledFuture = JVM_RUNNING_TASK.get(taskCode, false);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        JVM_RUNNING_TASK.remove(taskCode);
    }

    public List runningTaskList() {
        List res = new ArrayList();
        Iterator<CacheObj<String, ScheduledFuture>> cacheObjIterator = JVM_RUNNING_TASK.cacheObjIterator();
        while (cacheObjIterator.hasNext()) {
            CacheObj<String, ScheduledFuture> next = cacheObjIterator.next();
            String key = next.getKey();
            res.add(key);
        }
        log.info(threadPoolTaskScheduler.getScheduledThreadPoolExecutor().toString());
        return res;
    }

    public void init(TaskJob taskJob) {
        taskStore.storeTask(taskJob);
        TaskJob storeTask = taskStore.findByTaskCode(taskJob.getTaskCode());
        if (storeTask.getEnable() && storeTask.getTaskState().equals(TaskStateEnum.START)) {
            this.startJVMJob(storeTask, new HashMap());
        }
    }


    public synchronized void startJob(String taskCode) {
        TaskJob taskJob = taskStore.findByTaskCode(taskCode);
        if (taskJob.getEnable()) {
            removeJvmTask(taskCode);
            startJVMJob(taskJob, null);
            sysTaskService.update(Wrappers.<SysTask>lambdaUpdate().set(SysTask::getTaskState, TaskStateEnum.START).eq(SysTask::getTaskCode, taskCode));
        }

    }


    public synchronized void stopJob(String taskCode) {
        removeJvmTask(taskCode);
        sysTaskService.update(Wrappers.<SysTask>lambdaUpdate().set(SysTask::getTaskState, TaskStateEnum.STOP).eq(SysTask::getTaskCode, taskCode));
    }

}
