package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.config.LakerConfig;
import com.laker.admin.framework.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.logging.LoggerGroups;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-05
 */
@Controller
@RequestMapping("/sys/weblog")
public class WebLogController {
    @Autowired
    LakerConfig lakerConfig;
    @Autowired
    private LoggingSystem loggingSystem;
    @Autowired
    private LoggerGroups loggerGroups;


    @GetMapping(value = "/file", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    @SaCheckPermission("weblog.list")
    public Resource logFile(@RequestParam(required = false) String filePath) {
        if (StrUtil.isNotBlank(filePath)) {
            throw new BusinessException("演示环境，禁止修改模式文件路径");
        }

        String lookFilePath = lakerConfig.getLogFilePath();
        if (StrUtil.isNotBlank(filePath)) {
            lookFilePath = filePath;
        }
        Resource logFileResource = new FileSystemResource(new File(lookFilePath));
        if (logFileResource == null || !logFileResource.isReadable()) {
            return null;
        }
        return logFileResource;
    }

    @RequestMapping(value = "/level") //动态设置日志级别
    @ResponseBody
    @SaCheckPermission("weblog.update")
    public String configureLogLevel(String name, LogLevel configuredLevel) {
        if (StrUtil.isBlank(name)) {
            return "请输入日志名称";
        }
        LoggerGroup group = this.loggerGroups.get(name);
        if (group != null && group.hasMembers()) {
            group.configureLogLevel(configuredLevel, this.loggingSystem::setLogLevel);
            return "ok";
        }
        this.loggingSystem.setLogLevel(name, configuredLevel);
        return "ok";
    }

}