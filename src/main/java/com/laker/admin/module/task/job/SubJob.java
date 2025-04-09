package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import org.springframework.stereotype.Component;

import java.util.Map;

@EasyJob(taskCode = "subJob", taskName = "子任务", parentTaskCode = "parentJob")
@Component
public class SubJob implements IEasyJob {

    @Override
    public void execute(Map map) throws Exception {
        System.out.println("子任务执行");
    }
}