package com.laker.admin.framework.ext.flow;

import lombok.extern.slf4j.Slf4j;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.snaker.engine.handlers.IHandler;

import java.util.List;
import java.util.Map;

/**
 * 自定义模型操作处理类，用于完成流程的全自动编排
 * <a href="https://yunmel.gitbooks.io/snakerflow/content/4xiang-xi-shuo-ming/418-zi-ding-yi-jie-dian.html">...</a>
 */
@Slf4j
public class EasyCustomHandler implements IHandler {
    @Override
    public void handle(Execution execution) {
        // 获取参数
        Map<String, Object> args = execution.getArgs();
        args.forEach((s, o) -> log.info("参数为:{}:{}", s, o));
        List<Task> tasks = execution.getTasks();
        tasks.forEach(task -> log.info("任务名称为:{}", task.getTaskName()));
    }
}
