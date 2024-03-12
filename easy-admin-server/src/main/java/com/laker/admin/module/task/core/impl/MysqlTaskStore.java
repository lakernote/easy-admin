package com.laker.admin.module.task.core.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.module.enums.TaskStateEnum;
import com.laker.admin.module.task.core.ITaskStore;
import com.laker.admin.module.task.core.TaskDto;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MysqlTaskStore implements ITaskStore {

    @Autowired
    ISysTaskService taskService;

    @Override
    public void saveTask(TaskDto taskDto) {
        SysTask task = new SysTask();
        task.setEnable(true);

        BeanUtils.copyProperties(taskDto, task);
        try {
            long count = taskService.count(Wrappers.<SysTask>lambdaQuery().eq(SysTask::getTaskCode, taskDto.getTaskCode()));
            if (count == 0) {
                task.setTaskState(TaskStateEnum.START);
                taskService.save(task);
            }
        } finally {

        }
    }


    @Override
    public TaskDto findByTaskCode(String taskCode) {
        SysTask task = taskService.getOne(Wrappers.<SysTask>lambdaQuery().eq(SysTask::getTaskCode, taskCode));
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(task, taskDto);
        return taskDto;
    }


}
