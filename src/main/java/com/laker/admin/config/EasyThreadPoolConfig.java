package com.laker.admin.config;

import com.laker.admin.framework.ext.thread.EasyAdminMDCThreadPoolExecutor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Map;

@EnableScheduling
@Configuration
public class EasyThreadPoolConfig {


    /**
     * 定时任务线程池
     */
    @Bean
    public ThreadPoolTaskScheduler easyTaskThreadPool() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix("easy-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
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
        threadPoolTaskExecutor.setThreadNamePrefix("easy-");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
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

}
