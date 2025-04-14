package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.IEasyJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

//@EasyJob(taskCode = "subJob", taskName = "子任务", parentTaskCode = "parentJob")
@Slf4j
public class SubJob implements IEasyJob {

    @Override
    public void execute(Map map) throws Exception {
        log.info("子任务执行");
    }
}