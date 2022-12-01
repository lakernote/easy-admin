package com.laker.admin.module.flow.process;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysUserService;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.WorkItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BaseFlowController {
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
    @Autowired
    private ISysUserService sysUserService;

    protected void setFlowStatusInfo(BaseFlowStatus baseFlowStatus) {
        String orderId = baseFlowStatus.getOrderId();
        // 流程实例状态
        HistoryOrder histOrder = snakerEngineFacets.getEngine().query().getHistOrder(orderId);
        if (histOrder != null) {
            baseFlowStatus.setOrderState(histOrder.getOrderState());
        }
        List<WorkItem> workItems = snakerEngineFacets.getEngine().query().getWorkItems(null, new QueryFilter().setOrderId(orderId));
        if (CollUtil.isNotEmpty(workItems)) {
            WorkItem workItem = workItems.get(0);
            // 当前流程实例任务节点名称
            baseFlowStatus.setTaskName(workItem.getTaskName());
            List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query().getHistoryTasks(new QueryFilter()
                    .setOrderId(orderId)
                    .setOperator(StpUtil.getLoginIdAsString())
                    .orderBy("create_time")
                    .order(QueryFilter.DESC));
            if (CollUtil.isNotEmpty(historyTasks)) {
                baseFlowStatus.setCreateTaskId(historyTasks.get(0).getId());
            }
            // 当前流程实例任务操作人
            String[] actors = snakerEngineFacets.getEngine().query().getTaskActorsByTaskId(workItem.getTaskId());
            if (ArrayUtil.isNotEmpty(actors)) {
                List<String> res = new ArrayList<>();
                for (String actor : actors) {
                    SysUser user = sysUserService.getById(Long.valueOf(actor));
                    if (user != null) {
                        res.add(user.getNickName());
                    }
                }
                baseFlowStatus.setTaskOperatorName(StrUtil.join(",", res));
            }
        }
    }
}
