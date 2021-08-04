package com.laker.admin.module.task.core;

public interface ICallBack {
    void start(TaskDto taskDto);

    void exception(TaskDto taskDto, Exception e);

    void end(TaskDto taskDto);
}
