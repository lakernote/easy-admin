package com.laker.admin.module.task.core.impl;

import com.laker.admin.module.task.core.ICallBack;
import com.laker.admin.module.task.core.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogCallBack implements ICallBack {
    @Override
    public void start(TaskDto taskDto) {
        log.info("start " + taskDto.getTaskCode() + " " + taskDto.getTaskName());
    }

    @Override
    public void exception(TaskDto taskDto, Exception e) {
        log.error(taskDto.toString(), e);
    }

    @Override
    public void end(TaskDto taskDto) {
        log.info("end " + taskDto.getTaskCode() + " " + taskDto.getTaskName());
    }
}
