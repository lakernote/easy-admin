package com.laker.admin.module.ext.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.aop.duplicate.EasyDuplicateRequestLimiter;
import com.laker.admin.framework.aop.metrics.EasyMetrics;
import com.laker.admin.framework.aop.trace.EasyIgnoreTrace;
import com.laker.admin.framework.aop.trace.EasyTrace;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.mapper.ExtLogMapper;
import com.laker.admin.module.ext.service.IExtLogService;
import com.laker.admin.module.ext.vo.LogStatisticsTop10Vo;
import com.laker.admin.module.ext.vo.LogStatisticsVo;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 */
@Tag(name = "△△△3.请求日志△△△")
@RestController
@RequestMapping("/ext/log")
@EasyMetrics
@EasyTrace
public class ExtLogController {
    final IExtLogService extLogService;

    final ExtLogMapper extLogMapper;

    final ISysUserService sysUserService;

    public ExtLogController(IExtLogService extLogService, ExtLogMapper extLogMapper, ISysUserService sysUserService) {
        this.extLogService = extLogService;
        this.extLogMapper = extLogMapper;
        this.sysUserService = sysUserService;
    }

    @GetMapping
    @SaCheckPermission("log.list")
    @EasyIgnoreTrace
    @EasyDuplicateRequestLimiter(businessKey = "log.list", businessParam = "#keyWord")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                String keyWord) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLog> queryWrapper = new QueryWrapper().lambda();
        if (StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like(ExtLog::getRequest, keyWord);
        }
        queryWrapper.orderByDesc(ExtLog::getCreateTime);
        Page pageList = extLogService.page(roadPage, queryWrapper);
        List<ExtLog> records = pageList.getRecords();
        records.forEach(extLog -> {
            if (extLog.getUserId() != null) {
                SysUser sysUser = sysUserService.getById(extLog.getUserId());
                extLog.setUser(sysUser);
            }

        });
        return PageResponse.ok(records, pageList.getTotal());
    }


    @GetMapping("/visits7day")
    public Response visits7day() {
        List<LogStatisticsVo> logStatisticsVo = extLogMapper.selectStatistics7Day();
        return Response.ok(logStatisticsVo);
    }

    @GetMapping("/visitsTop10IP")
    public PageResponse visitsTop10IP() {
        List<LogStatisticsTop10Vo> logStatisticsVo = extLogMapper.selectStatisticsVisitsTop10IP();
        return PageResponse.ok(logStatisticsVo, 10L);
    }
}