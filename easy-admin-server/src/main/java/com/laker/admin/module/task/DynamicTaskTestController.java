package com.laker.admin.module.task;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
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
@ApiSupport(order = 1)
@RestController
@Slf4j
public class DynamicTaskTestController {

    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> future;


    @GetMapping("/startCron")
    public String startCron() {

        future = taskScheduler.schedule(new MyRunnable(), new CronTrigger("0/5 * * * * *"));
        log.info("DynamicTask.startCron()");
        return "startCron";
    }

    @DeleteMapping("/stopCron")
    public String stopCron() {

        if (future != null) {
            future.cancel(true);
        }
        log.info("DynamicTask.stopCron()");
        return "stopCron";
    }

    @GetMapping("/changeCron10")
    public String startCron10() {

        stopCron();// 先停止，在开启.
        future = taskScheduler.schedule(new MyRunnable(), new CronTrigger("*/10 * * * * *"));
        log.info("DynamicTask.startCron10()");
        return "changeCron10";
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            log.info("DynamicTask.MyRunnable.run()，" + new Date());
        }
    }
}