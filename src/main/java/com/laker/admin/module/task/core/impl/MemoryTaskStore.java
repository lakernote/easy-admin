package com.laker.admin.module.task.core.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import com.laker.admin.module.task.core.ITaskStore;
import com.laker.admin.module.task.core.TaskDto;
import com.laker.admin.module.task.core.TaskStateEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MemoryTaskStore implements ITaskStore {

    private final static LFUCache<String, TaskDto> TASK_CACHE = CacheUtil.newLFUCache(1000, TimeUnit.DAYS.toMillis(3));


    @Override
    public void saveTask(TaskDto taskDto) {
        TASK_CACHE.put(taskDto.getTaskCode(), taskDto);
    }

    @Override
    public List<TaskDto> list() {
        List list = new ArrayList();
        TASK_CACHE.forEach(taskDto -> {
            list.add(taskDto);
        });
        return list;
    }

    @Override
    public TaskDto updateTaskByTaskCode(String taskCron, String taskName, String taskCode) {
        TaskDto taskDtoSave = this.findByTaskCode(taskCode);
        taskDtoSave.setTaskCron(taskCron);
        taskDtoSave.setTaskName(taskName);
        TASK_CACHE.put(taskCode, taskDtoSave);
        return taskDtoSave;
    }

    @Override
    public TaskDto updateTaskStateByTaskCode(TaskStateEnum taskState, String taskCode) {
        TaskDto taskDtoSave = this.findByTaskCode(taskCode);
        taskDtoSave.setTaskState(taskState);
        TASK_CACHE.put(taskCode, taskDtoSave);
        return taskDtoSave;
    }

    @Override
    public void deleteTaskByTaskCode(String taskCode) {
        TaskDto taskDtoSave = this.findByTaskCode(taskCode);
        TASK_CACHE.put(taskCode, taskDtoSave);
    }

    @Override
    public TaskDto findByTaskCode(String taskCode) {
        return TASK_CACHE.get(taskCode, false);
    }


}
