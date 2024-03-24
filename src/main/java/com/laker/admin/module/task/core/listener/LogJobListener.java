package com.laker.admin.module.task.core.listener;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.laker.admin.module.task.core.TaskJob;
import com.laker.admin.module.task.entity.SysTasklog;
import com.laker.admin.module.task.service.ISysTasklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author laker
 */
@Component
@Slf4j
public class LogJobListener implements JobListener {
    ThreadLocal<SysTasklog> threadLocal = ThreadUtil.createThreadLocal(true);
    @Autowired
    ISysTasklogService sysTasklogService;

    @Override
    public void start(TaskJob taskJob) {
        SysTasklog tasklog = new SysTasklog();
        tasklog.setStartTime(LocalDateTime.now());
        tasklog.setTaskCode(taskJob.getTaskCode());
        tasklog.setStatus(1);
        tasklog.setThreadName(Thread.currentThread().getName());
        sysTasklogService.save(tasklog);
        threadLocal.set(tasklog);
        log.debug("start taskCode: {},taskName:{}", taskJob.getTaskCode(), taskJob.getTaskName());
    }

    @Override
    public void exception(TaskJob taskJob, Exception e) {
        SysTasklog tasklog = threadLocal.get();
        tasklog.setStatus(2);
        sysTasklogService.updateById(tasklog);
        log.error(taskJob.toString(), e);
    }

    @Override
    public void end(TaskJob taskJob) {
        SysTasklog tasklog = threadLocal.get();
        try {
            tasklog.setEndTime(LocalDateTime.now());
            tasklog.setCost((int) LocalDateTimeUtil.between(tasklog.getStartTime(), tasklog.getEndTime()).toMillis());
            sysTasklogService.updateById(tasklog);
        } finally {
            threadLocal.remove();
            log.debug("end taskCode: {},taskName:{}", taskJob.getTaskCode(), taskJob.getTaskName());
        }
    }
}
