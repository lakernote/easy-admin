package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IJob;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@EasyJob(taskCode = "job2", taskName = "laker测试任务")
@Slf4j
public class Test2Job implements IJob {
    @Override
    public void execute(Map map) throws Exception {
        log.info("laker job2 run");
        TimeUnit.SECONDS.sleep(10);
    }
}
