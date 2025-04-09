package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@EasyJob(taskCode = "parentJob", taskName = "父任务", fixedDelay = 3)
@Component
@Slf4j
public class ParentJob implements IEasyJob {

    @Override
    public void execute(Map map) throws Exception {
        log.info("父任务执行");
    }
}