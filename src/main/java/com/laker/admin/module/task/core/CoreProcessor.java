package com.laker.admin.module.task.core;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.framework.utils.SpringUtils;
import com.laker.admin.module.enums.TaskStateEnum;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CoreProcessor implements CommandLineRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private ITaskStore taskStore;

    @Autowired
    private List<ICallBack> callBacks;

    @Autowired
    private ISysTaskService sysTaskService;

    /**
     * 启动后存储任务列表
     */
    private final static LFUCache<String, ScheduledFuture> JVM_RUNNING_TASK = CacheUtil.newLFUCache(1000, TimeUnit.DAYS.toMillis(3));


    @Override
    public void run(String... args) throws Exception {
        // 获取所有实现了IJob接口的类
        Map<String, IJob> beansOfType = applicationContext.getBeansOfType(IJob.class);
        if (MapUtil.isEmpty(beansOfType)) {
            log.warn("未查询到IJob实现类");
            return;
        }
        // 遍历加入到任务执行器中
        beansOfType.forEach((s, job) -> {
            Job annotation = AnnotationUtils.findAnnotation(job.getClass(), Job.class);
            if (annotation != null) {
                String cron = annotation.cron();
                String taskName = annotation.taskName();
                String taskCode = annotation.taskCode();
                TaskDto taskDto = TaskDto.builder()
                        .taskCode(taskCode)
                        .taskName(taskName)
                        .taskCron(cron)
                        .taskClassName(job.getClass().getName()).build();
                taskStore.saveTask(taskDto);
                TaskDto storeTask = taskStore.findByTaskCode(taskCode);
                if (storeTask.getEnable() && storeTask.getTaskState().equals(TaskStateEnum.START)) {
                    this.startJVMJob(storeTask, null);
                }
            }
        });
    }

    /**
     * 将任务加入到JVM执行器中
     *
     * @return
     */
    private void startJVMJob(TaskDto task, Map param) {
        String taskCron = task.getTaskCron();
        // TODO taskCron CHECK
        if (StrUtil.isNotBlank(taskCron) && !StrUtil.equals(taskCron, "-")) {
            ScheduledFuture<?> future = threadPoolTaskScheduler
                    .schedule(() -> {
                                try {
                                    callBacks.forEach(iCallBack -> {
                                        iCallBack.start(task);
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
                                    callBacks.forEach(iCallBack -> {
                                        iCallBack.exception(task, e);
                                    });
                                } finally {
                                    callBacks.forEach(iCallBack -> {
                                        iCallBack.end(task);
                                    });
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

    public List jvmTaskList() {
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


    public synchronized void startJob(String taskCode) {
        TaskDto taskDto = taskStore.findByTaskCode(taskCode);
        if (taskDto.getEnable()) {
            removeJvmTask(taskCode);
            startJVMJob(taskDto, null);
            sysTaskService.update(Wrappers.<SysTask>lambdaUpdate().set(SysTask::getTaskState, TaskStateEnum.START).eq(SysTask::getTaskCode, taskCode));
        }

    }


    public synchronized void stopJob(String taskCode) {
        removeJvmTask(taskCode);
        sysTaskService.update(Wrappers.<SysTask>lambdaUpdate().set(SysTask::getTaskState, TaskStateEnum.STOP).eq(SysTask::getTaskCode, taskCode));
    }


}
