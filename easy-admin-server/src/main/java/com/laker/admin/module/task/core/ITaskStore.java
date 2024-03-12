package com.laker.admin.module.task.core;

public interface ITaskStore {

    void saveTask(TaskDto taskDto);


    TaskDto findByTaskCode(String taskCode);

}
