package com.laker.admin.module.task.core.runner;

import cn.hutool.core.map.MapUtil;
import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IJob;
import com.laker.admin.module.task.core.TaskJob;
import com.laker.admin.module.task.core.processor.EasyTaskProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author laker
 */
@Component
@Slf4j
public class EasyTaskCommandLineRunner implements CommandLineRunner {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private EasyTaskProcessor easyTaskProcessor;

    @Override
    public void run(String... args) throws Exception {
        // 获取所有实现了IJob接口的类
        Map<String, IJob> beansOfType = applicationContext.getBeansOfType(IJob.class);
        if (MapUtil.isEmpty(beansOfType)) {
            log.warn("未查询到IJob实现类");
            return;
        }
        // 遍历加入到任务执行器中
        beansOfType.forEach((s, job) -> {
            EasyJob annotation = AnnotationUtils.findAnnotation(job.getClass(), EasyJob.class);
            if (annotation != null) {
                String cron = annotation.cron();
                String taskName = annotation.taskName();
                String taskCode = annotation.taskCode();
                TaskJob taskJob = TaskJob.builder()
                        .taskCode(taskCode)
                        .taskName(taskName)
                        .taskCron(cron)
                        .taskClassName(job.getClass().getName()).build();
                easyTaskProcessor.init(taskJob);
            }
        });
    }
}
