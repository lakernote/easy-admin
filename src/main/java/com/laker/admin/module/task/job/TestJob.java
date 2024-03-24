package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.IJob;
import com.laker.admin.module.task.core.EasyJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@EasyJob(taskCode = "job1", cron = "1/3 * * * * ?", taskName = "laker测试任务")
@Slf4j
public class TestJob implements IJob {
    @Override
    public void execute(Map map) throws Exception {
        log.info("laker job1 run");
        TimeUnit.SECONDS.sleep(1);
    }
}
