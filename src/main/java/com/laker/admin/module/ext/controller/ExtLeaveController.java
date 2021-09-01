package com.laker.admin.module.ext.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.EasyAdminSecurityUtils;
import com.laker.admin.framework.PageResponse;
import com.laker.admin.framework.Response;
import com.laker.admin.framework.aop.Metrics;
import com.laker.admin.module.ext.entity.ExtLeave;
import com.laker.admin.module.ext.service.IExtLeaveService;
import com.laker.admin.module.flow.process.BaseFlowController;
import com.laker.admin.module.flow.process.SnakerEngineFacets;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-19
 */
@RestController
@RequestMapping("/ext/leave")
@Metrics
public class ExtLeaveController extends BaseFlowController {
    @Autowired
    IExtLeaveService extLeaveService;
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
    @Autowired
    private ISysUserService sysUserService;


    @GetMapping
    @ApiOperation(value = "分页查询")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLeave> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.orderByDesc(ExtLeave::getCreateTime);
        Page pageList = extLeaveService.page(roadPage, queryWrapper);
        List<ExtLeave> records = pageList.getRecords();
        records.forEach(extLeave -> {
            extLeave.setCreateUser(sysUserService.getUserAndDeptById(extLeave.getCreateBy()));
            this.setFlowStatusInfo(extLeave);

        });
        return PageResponse.ok(records, pageList.getTotal());
    }

    @PostMapping
    @ApiOperation(value = "发起请假")
    @Transactional(rollbackFor = Exception.class)
    public Response saveOrUpdate(@RequestBody ExtLeave param) {
        if (param.getLeaveId() == null) {
            param.setLeaveUserId(StpUtil.getLoginIdAsLong());
            Map args = new HashMap(8);
            // 当前登录人
            args.put("user1", StpUtil.getLoginIdAsString());
            // 部门经理岗位的人 去用户表查询当前登录人同部门 and 岗位 = 部门经理
            args.put("user2", "17");
            // 总经理岗位的人   去用户表查询当前登录人同部门 and 岗位 = 总经理
            args.put("user3", "18");
            args.put("day", param.getLeaveDay());
            args.put(SnakerEngine.ID, EasyAdminSecurityUtils.getCurrentUserInfo().getNickName() + "-" + DateUtil.now() + "的请假申请");
            Order leave = snakerEngineFacets.startAndExecute("leave", 2, StpUtil.getLoginIdAsString(), args);
            param.setOrderId(leave.getId());
            extLeaveService.saveOrUpdate(param);
        } else {
            extLeaveService.saveOrUpdate(param);
        }


        return Response.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")
    public Response get(@PathVariable Long id) {
        return Response.ok(extLeaveService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除")
    public Response delete(@PathVariable Long id) {
        return Response.ok(extLeaveService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    @ApiOperation(value = "根据批量删除ids删除")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(extLeaveService.removeByIds(CollUtil.toList(ids)));
    }
}