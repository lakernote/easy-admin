package com.laker.admin.module.ext.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequest;
import com.laker.admin.module.enums.DemoTypeEnum;
import com.laker.admin.module.enums.Distance;
import com.laker.admin.module.ext.vo.qo.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "示例-各种请求参数示例")
@ApiSupport(order = 100)
@RestController
@RequestMapping("/ext/demo")
@Slf4j
public class DemoController {

    @GetMapping
    @ApiOperation(value = "参数数组 - querystring")
    public void pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                        @RequestParam(required = false, defaultValue = "10") long limit,
                        @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info("page:{},limit:{},types:{}", page, limit, types.toString());
    }

    @GetMapping("/1")
    @ApiOperation(value = "枚举 - querystring")
    public void pageAll2(Distance distance) {
        log.info(distance.toString());
    }

    @GetMapping("/2")
    @ApiOperation(value = "对象 - querystring")
    public void pageAll3(City city) {
        log.info(city.toString());
    }

    @PostMapping("/3")
    @ApiOperation(value = "实体 - json")
    public City pageAll4(@RequestBody City city) {
        log.info(city.toString());
        return city;
    }

    @PostMapping("/4")
    @ApiOperation(value = "实体 - json")
    public void pageAll5(@RequestBody City city, CurrentUser user) {
        log.info("city:{},user{}", city, user.toString());
    }

    @GetMapping("/5")
    @ApiOperation(value = "请求参数的PageRequest对象自动填充 - PageRequest")
    public void pageAll6(PageRequest user) {
        log.info(user.toString());
    }

    @PostMapping("/6")
    @ApiOperation(value = "请求参数的CurrentUser对象自动填充 - CurrentUser")
    public void pageAll5(CurrentUser user) {
        log.info("user{}", user.toString());
    }
}
