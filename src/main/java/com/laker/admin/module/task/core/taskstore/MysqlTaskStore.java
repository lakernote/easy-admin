package com.laker.admin.module.task.core.taskstore;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laker.admin.module.enums.TaskStateEnum;
import com.laker.admin.module.task.core.TaskJob;
import com.laker.admin.module.task.entity.SysTask;
import com.laker.admin.module.task.service.ISysTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MysqlTaskStore implements ITaskStore {

    @Autowired
    ISysTaskService taskService;

    @Override
    public void storeTask(TaskJob taskJob) {
        SysTask task = new SysTask();
        BeanUtils.copyProperties(taskJob, task);
        int count = taskService.count(Wrappers.<SysTask>lambdaQuery().eq(SysTask::getTaskCode, taskJob.getTaskCode()));
        if (count == 0) {
            task.setEnable(true);
            if (StrUtil.equals(task.getTaskCron(), "-")) {
                task.setEnable(false);
            }
            task.setCreateTime(LocalDateTime.now());
            task.setTaskState(TaskStateEnum.START);
            taskService.save(task);
        }
    }


    @Override
    public TaskJob findByTaskCode(String taskCode) {
        SysTask task = taskService.getOne(Wrappers.<SysTask>lambdaQuery().eq(SysTask::getTaskCode, taskCode));
        TaskJob taskJob = new TaskJob();
        BeanUtils.copyProperties(task, taskJob);
        return taskJob;
    }


}
