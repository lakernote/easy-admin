package com.laker.admin.module.ext.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequest;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.enums.DemoTypeEnum;
import com.laker.admin.module.enums.Distance;
import com.laker.admin.module.ext.vo.qo.City;
import com.laker.admin.module.remote.IpifyClient;
import com.laker.admin.module.remote.dto.IpifyVo;
import com.laker.admin.module.sys.entity.SysUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Tag(name = "△△△1.示例controller△△△")// 在doc.html中的名称
@ApiSupport(order = 0, author = "laker") // 在doc.html中的顺序
@RestController
@RequestMapping("/demo/v1")
@Slf4j
public class DemoController {

    @Autowired
    IpifyClient ipifyClient;


    @GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "0.Param+Header+Path 参数数组 - querystring")
    @Parameters({
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", example = "xxxx", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "page", description = "第几页", in = ParameterIn.QUERY),
            @Parameter(name = "limit", description = "每页多少记录", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "types", description = "类型", required = false, in = ParameterIn.QUERY)
    })
    public Response<SysUser> pageList(
            @PathVariable("id") Long id,
            @RequestHeader("token") String token,
            @RequestParam(required = false, defaultValue = "1") long page,
            @RequestParam(required = false, defaultValue = "10") long limit,
            @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info("id:{},token:{},page:{},limit:{},types:{}", id, token, page, limit, types);
        return PageResponse.ok(new SysUser());
    }

    @GetMapping("/1")
    @Operation(summary = "枚举 - querystring")
    public void pageAll2(Distance distance) {
        log.info(distance.toString());
    }

    @GetMapping("/2")
    @Operation(summary = "实体 - querystring")
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

    @GetMapping("/remote-call")
    @Operation(summary = "6.远程调用-获取ip地址")
    public Response<IpifyVo> remoteCall(
            @Parameter(description = "traceId,用于链路追踪", example = "67614c197f54471795cc84a3a073dd25", required = true, in = ParameterIn.HEADER)
            @RequestHeader String traceId) {
        return Response.ok(ipifyClient.getIpAddress());
    }
}