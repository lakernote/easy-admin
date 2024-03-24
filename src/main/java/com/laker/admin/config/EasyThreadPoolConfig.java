package com.laker.admin.config;

import com.laker.admin.framework.ext.thread.EasyAdminMDCThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

@EnableScheduling
@Configuration
public class EasyThreadPoolConfig {


    /**
     * 内置定时任务所用的线程池
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix("easy-task-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }

    /**
     * 业务线程池，每个业务一个线程池，用于仓壁隔离
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor remotePool() {
        return new EasyAdminMDCThreadPoolExecutor(5, 5, "remote");
    }
}
