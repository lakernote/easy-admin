package com.laker.admin.module.task.job;

import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@EasyJob(taskCode = "shardingJob", taskName = "分片任务", shardTotal = 3, timeout = 30)
@Component
@Slf4j
public class ShardingJob implements IEasyJob {
    @Override
    public void execute(Map map) throws Exception {
        // 这里需要实现具体的分片逻辑
        log.info("分片任务执行");
    }
}    