package com.laker.admin.module.task;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.task.core.CoreProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order = 1)
@RestController
@RequestMapping("/task-manager-monitor")
@Slf4j
public class TaskManagerMonitorController {

    private final CoreProcessor coreProcessor;

    public TaskManagerMonitorController(CoreProcessor coreProcessor) {
        this.coreProcessor = coreProcessor;
    }

    @GetMapping("/jvm-task-list")
    public Response<List<String>> startCron() {
        return Response.ok(coreProcessor.jvmTaskList());
    }


}