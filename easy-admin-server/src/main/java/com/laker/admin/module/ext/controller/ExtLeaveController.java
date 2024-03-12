package com.laker.admin.module.ext.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.aop.trace.LakerTrace;
import com.laker.admin.framework.aop.trace.SpanType;
import com.laker.admin.framework.aop.trace.TraceCodeBlock;
import com.laker.admin.framework.ext.thread.EasyAdminMDCThreadPoolExecutor;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.framework.utils.EasyAdminSecurityUtils;
import com.laker.admin.module.ext.entity.ExtLeave;
import com.laker.admin.module.ext.service.IExtLeaveService;
import com.laker.admin.module.flow.process.BaseFlowController;
import com.laker.admin.module.flow.process.SnakerEngineFacets;
import com.laker.admin.module.sys.service.ISysUserService;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    ThreadPoolExecutor pool = new EasyAdminMDCThreadPoolExecutor(5,5,"laker");

    @GetMapping
    @LakerTrace(spanType = SpanType.Controller)
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                @RequestParam(required = false) String keyWord) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLeave> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.like(StrUtil.isNotBlank(keyWord),ExtLeave::getLeaveReason,keyWord);
        queryWrapper.orderByDesc(ExtLeave::getCreateTime);
//        Page pageList = extLeaveService.page(roadPage, queryWrapper);
        IPage pageList = TraceCodeBlock.trace("leaveService.page",
                () -> extLeaveService.page(roadPage, queryWrapper));

        List<ExtLeave> records = pageList.getRecords();
        records.forEach(extLeave -> {
            extLeave.setCreateUser(sysUserService.getUserAndDeptById(extLeave.getCreateBy()));
            this.setFlowStatusInfo(extLeave);

        });

        pool.execute(() -> {
            TraceCodeBlock.trace("6666666666666666",value -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        pool.submit(() -> {
           TraceCodeBlock.trace("1231233",value -> {
               try {
                   TimeUnit.MILLISECONDS.sleep(300);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           });
        });
        sysUserService.getUserDataPowers(StpUtil.getLoginIdAsLong());
        return PageResponse.ok(records, pageList.getTotal());
    }

    /**
     * 暂时是为了测试 多表查询别名情况的数据权限过滤。
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/v2")
    public PageResponse pageAllV2(@RequestParam(required = false, defaultValue = "1") long page,
                                  @RequestParam(required = false, defaultValue = "10") long limit) {
        Page roadPage = new Page<>(page, limit);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ge("l.leave_day", 1);
        queryWrapper.orderByDesc("l.create_time");
        IPage pageList = extLeaveService.pageV2(roadPage, queryWrapper);
        return PageResponse.ok(pageList.getRecords(), pageList.getTotal());
    }

    @PostMapping
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
    public Response get(@PathVariable Long id) {
        return Response.ok(extLeaveService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return Response.ok(extLeaveService.removeById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Response batchRemove(@PathVariable Long[] ids) {
        return Response.ok(extLeaveService.removeByIds(CollUtil.toList(ids)));
    }
}