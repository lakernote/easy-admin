package com.laker.admin.config;

import com.laker.admin.framework.ext.thread.EasyAdminMDCThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class EasyThreadPoolConfig {

    static {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> log.error("Thread {} got exception", thread, throwable));
    }

    /**
     * 定时任务线程池
     */
    @Bean
    public ThreadPoolTaskScheduler easyTaskThreadPool() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix("easy-job-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        threadPoolTaskScheduler.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());
        return threadPoolTaskScheduler;
    }

    /**
     * 业务线程池
     */
    @Bean
    public ThreadPoolTaskExecutor easyThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(20);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setThreadNamePrefix("easy-executor-");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());
        threadPoolTaskExecutor.setTaskDecorator(runnable -> {
            final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    MDC.setContextMap(copyOfContextMap);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        });
        return threadPoolTaskExecutor;
    }

    /**
     * 业务线程池
     */
    @Bean
    public EasyAdminMDCThreadPoolExecutor businessMDCThreadPool() {
        return new EasyAdminMDCThreadPoolExecutor(20, 100, "business");
    }

    // 自定义的拒绝执行处理器，以更好地处理任务被拒绝的情况
    static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 这里可以加入更详细的日志记录或发送警报等逻辑
            log.warn("Task " + r.toString() + " rejected from " + executor.toString());
        }
    }


}
