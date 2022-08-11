package com.laker.admin.module.ext.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.module.enums.DemoTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Api(tags = "示例controller")// 在doc.html中的名称
@ApiSupport(order = 0, author = "laker") // 在doc.html中的顺序
@RestController
@RequestMapping("/ext/demo")
@Slf4j
public class DemoController {

    @GetMapping
    @ApiOperation(value = "参数数组 - querystring")
    public void pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                        @RequestParam(required = false, defaultValue = "10") long limit,
                        @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info(types.toString());
    }

}