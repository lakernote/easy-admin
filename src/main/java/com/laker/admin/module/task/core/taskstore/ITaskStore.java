package com.laker.admin.module.task.core.taskstore;

import com.laker.admin.module.task.core.TaskJob;

public interface ITaskStore {

    void storeTask(TaskJob taskJob);


    TaskJob findByTaskCode(String taskCode);

}
