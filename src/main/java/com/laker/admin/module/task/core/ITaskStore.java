package com.laker.admin.module.task.core;

import java.util.List;

public interface ITaskStore {

    void saveTask(TaskDto taskDto);

    List<TaskDto> list();

    TaskDto updateTaskByTaskCode(String taskCron, String taskName, String taskCode);

    TaskDto updateTaskStateByTaskCode(TaskStateEnum taskState, String taskCode);

    void deleteTaskByTaskCode(String taskCode);

    TaskDto findByTaskCode(String taskCode);

}
