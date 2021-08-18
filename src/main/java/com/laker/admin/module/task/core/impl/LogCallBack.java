package com.laker.admin.module.task.core.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.laker.admin.module.task.core.ICallBack;
import com.laker.admin.module.task.core.TaskDto;
import com.laker.admin.module.task.entity.SysTasklog;
import com.laker.admin.module.task.service.ISysTasklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LogCallBack implements ICallBack {
    ThreadLocal<SysTasklog> threadLocal = ThreadUtil.createThreadLocal(true);
    @Autowired
    ISysTasklogService sysTasklogService;

    @Override
    public void start(TaskDto taskDto) {

        SysTasklog tasklog = new SysTasklog();
        tasklog.setStartTime(LocalDateTime.now());
        tasklog.setTaskCode(taskDto.getTaskCode());
        tasklog.setStatus(1);
        tasklog.setThreadName(Thread.currentThread().getName());
        sysTasklogService.save(tasklog);
        threadLocal.set(tasklog);
        log.info("start " + taskDto.getTaskCode() + " " + taskDto.getTaskName());
    }

    @Override
    public void exception(TaskDto taskDto, Exception e) {
        SysTasklog tasklog = threadLocal.get();
        tasklog.setStatus(2);
        sysTasklogService.updateById(tasklog);
        log.error(taskDto.toString(), e);
    }

    @Override
    public void end(TaskDto taskDto) {
        SysTasklog tasklog = threadLocal.get();
        tasklog.setEndTime(LocalDateTime.now());
        tasklog.setCost((int) LocalDateTimeUtil.between(tasklog.getStartTime(), tasklog.getEndTime()).toMillis());
        sysTasklogService.updateById(tasklog);
        threadLocal.remove();
        log.info("end " + taskDto.getTaskCode() + " " + taskDto.getTaskName());
    }
}
