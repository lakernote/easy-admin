package com.laker.admin.module.task.core.impl;

import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@EasyJob(taskCode = "job2", taskName = "laker测试任务")
@Slf4j
public class Test2EasyJob implements IEasyJob {
    @Override
    public void execute(Map map) throws Exception {
        log.info("laker job run");
        TimeUnit.SECONDS.sleep(10);
    }
}