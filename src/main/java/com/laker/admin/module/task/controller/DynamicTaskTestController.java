package com.laker.admin.module.task.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

// https://my.oschina.net/sdlvzg/blog/1590946
@Api(tags = "示例-动态任务原理测试")
@ApiSupport(order = 100)
@RestController
@Slf4j
public class DynamicTaskTestController {

    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> future;


    @GetMapping("/startCron")
    @ApiOperation("开始任务-每5s")
    public String startCron() {

        future = taskScheduler.schedule(new MyRunnable(), new CronTrigger("0/5 * * * * *"));
        log.info("DynamicTask.startCron()");
        return "startCron";
    }

    @DeleteMapping("/stopCron")
    @ApiOperation("取消任务")
    public String stopCron() {

        if (future != null) {
            future.cancel(true);
        }
        log.info("DynamicTask.stopCron()");
        return "stopCron";
    }

    @GetMapping("/changeCron10")
    @ApiOperation("更新任务- 每10s ")
    public String startCron10() {

        stopCron();// 先停止，在开启.
        future = taskScheduler.schedule(new MyRunnable(), new CronTrigger("*/10 * * * * *"));
        log.info("DynamicTask.startCron10()");
        return "changeCron10";
    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            log.info("DynamicTask.MyRunnable.run()，" + new Date());
        }
    }
}
