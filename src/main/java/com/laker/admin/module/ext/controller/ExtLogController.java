package com.laker.admin.module.ext.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.aop.repeatedsubmit.RepeatSubmitLimit;
import com.laker.admin.framework.aop.trace.EasyTraceIgnore;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.mapper.ExtLogMapper;
import com.laker.admin.module.ext.service.IExtLogService;
import com.laker.admin.module.ext.vo.LogStatisticsTop10Vo;
import com.laker.admin.module.ext.vo.LogStatisticsVo;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "示例-请求日志示例")
@ApiSupport(order = 100)
@RestController
@RequestMapping("/ext/log")
@Metrics
public class ExtLogController {
    @Autowired
    IExtLogService extLogService;

    @Autowired
    ExtLogMapper extLogMapper;

    @Autowired
    ISysUserService sysUserService;

    @GetMapping
    @ApiOperation(value = "日志分页查询")
    @SaCheckPermission("log.list")
    @EasyTraceIgnore
    @RepeatSubmitLimit(businessKey = "log.list", businessParam = "#keyWord")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                String keyWord) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLog> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like(ExtLog::getUri, keyWord);
        }
        queryWrapper.orderByDesc(ExtLog::getCreateTime);
        Page<ExtLog> pageList = extLogService.page(roadPage, queryWrapper);
        List<ExtLog> records = pageList.getRecords();
        records.forEach(extLog -> {
            if (extLog.getUserId() != null) {
                extLog.setUser(sysUserService.getUserAndDeptById(extLog.getUserId()));
            }
        });
        return PageResponse.ok(records, pageList.getTotal());
    }


    @GetMapping("/visits7day")
    @ApiOperation(value = "7天访问量")
    public Response<List<LogStatisticsVo>> visits7day() {
        List<LogStatisticsVo> logStatisticsVo = extLogMapper.selectStatistics7Day();
        return Response.ok(logStatisticsVo);
    }

    @GetMapping("/visitsTop10IP")
    @ApiOperation(value = "visitsTop10IP")
    public PageResponse<List<LogStatisticsTop10Vo>> visitsTop10IP() {
        List<LogStatisticsTop10Vo> logStatisticsVo = extLogMapper.selectStatisticsVisitsTop10IP();
        return PageResponse.ok(logStatisticsVo, 10L);
    }
}
