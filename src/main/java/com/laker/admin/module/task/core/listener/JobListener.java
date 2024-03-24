package com.laker.admin.module.task.core.listener;

import com.laker.admin.module.task.core.TaskJob;

public interface JobListener {
    void start(TaskJob taskJob);

    void exception(TaskJob taskJob, Exception e);

    void end(TaskJob taskJob);

}
