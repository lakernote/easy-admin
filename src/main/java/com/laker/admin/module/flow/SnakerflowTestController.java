package com.laker.admin.module.flow;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnakerflowTestController {

    @Autowired
    private SnakerflowTestService snakerflowTestService;


    /**
     * 主要用于测试snakerflow是否正常加载
     * 初始化流程
     *
     * @return String
     */
    @GetMapping("/getProcessList")
    @ApiOperation("初始化流程和获取所有流程")
    public String getProcess() {
        return snakerflowTestService.getProcess();
    }

    /**
     * 主要用于测试snakerflow是否正常加载
     * 初始化流程
     *
     * @return String
     */
    @GetMapping("/start")
    @ApiOperation("通过processId发起一个流程实例")
    public String start() {
        return snakerflowTestService.start();
    }
}