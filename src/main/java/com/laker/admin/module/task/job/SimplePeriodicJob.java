package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.IEasyJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

//@EasyJob(taskCode = "simplePeriodicJob", taskName = "简单周期性任务", fixedRate = 10)
@Slf4j
public class SimplePeriodicJob implements IEasyJob {
    @Override
    public void execute(Map map) {
        log.info("简单周期性任务执行");
    }
}    