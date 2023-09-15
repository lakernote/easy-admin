package com.laker.admin.module.ext.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequest;
import com.laker.admin.module.enums.DemoTypeEnum;
import com.laker.admin.module.enums.Distance;
import com.laker.admin.module.ext.vo.qo.City;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Tag(name  = "示例controller")// 在doc.html中的名称
@ApiSupport(order = 0, author = "laker") // 在doc.html中的顺序
@RestController
@RequestMapping("/ext/demo")
@Slf4j
public class DemoController {

    @GetMapping
    @Operation(summary  = "参数数组 - querystring")
    public void pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                        @RequestParam(required = false, defaultValue = "10") long limit,
                        @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info(types.toString());
    }

    @GetMapping("/1")
    @Operation(summary  = "枚举 - querystring")
    public void pageAll2(Distance distance) {
        log.info(distance.toString());
    }

    @GetMapping("/2")
    @Operation(summary  = "实体 - querystring")
    public void pageAll3(City city) {
        log.info(city.toString());
    }

    @PostMapping("/3")
    @Operation(summary = "实体 - json")
    public City pageAll4(@RequestBody City city) {
        log.info(city.toString());
        return city;
    }

    @PostMapping("/4")
    @Operation(summary = "实体 - json")
    public void pageAll5(@RequestBody City city, CurrentUser user) {
        log.info(user.toString());
    }

    @GetMapping("/5")
    @Operation(summary = "get - PageRequest-")
    public void pageAll6(PageRequest user) {
        log.info(user.toString());
    }
}