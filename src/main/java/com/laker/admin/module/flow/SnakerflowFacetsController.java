package com.laker.admin.module.flow;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.aop.repeatedsubmit.RepeatSubmitLimit;
import com.laker.admin.framework.model.PageResponse;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.flow.process.SnakerEngineFacets;
import com.laker.admin.module.flow.process.SnakerHelper;
import com.laker.admin.module.sys.entity.SysUser;
import com.laker.admin.module.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.*;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.snaker.engine.access.QueryFilter.DESC;

@Api(tags = "流程引擎-流程管理")// 在doc.html中的名称
@ApiSupport(order = 200, author = "laker") // 在doc.html中的顺序
@RestController
@RequestMapping("/flow")
@Slf4j
@SaCheckLogin
public class SnakerflowFacetsController {

    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
    @Autowired
    private ISysUserService sysUserService;

    // ---------流程相关 --------- //


    /**
     * 获取流程定义
     */
    @GetMapping("/getXml")
    public Response<String> getProcessXml(String id) {
        Process process = snakerEngineFacets.getEngine().process().getProcessById(id);
        return Optional.ofNullable(process.getDBContent())
                .map(content -> Response.ok(new String(content, StandardCharsets.UTF_8)))
                .orElse(Response.error500("xml异常"));
    }


    @GetMapping("/process/modelJson")
    @ApiOperation(value = "根据流程定义名称获取流程定义json", tags = "流程引擎-流程")
    @Metrics
    public String getProcessJson(@RequestParam(required = false) String processId) {
        if (StrUtil.isBlank(processId)) {
            return StrUtil.EMPTY;
        }
        Process process = snakerEngineFacets.getEngine().process().getProcessById(processId);
        if (Objects.isNull(process)) {
            return StrUtil.EMPTY;
        }
        ProcessModel processModel = process.getModel();
        if (Objects.isNull(processModel)) {
            return StrUtil.EMPTY;
        }
        return SnakerHelper.getModelJson(processModel);
    }

    /**
     * 流程定义查询列表
     */
    @ApiOperation(value = "根据给定的参数列表args分页查询process", tags = "流程引擎-流程")
    @RequestMapping(value = "/process/list", method = RequestMethod.GET)
    public Response<JSON> getProcessList(Page<Process> page, String displayName) {
        QueryFilter filter = new QueryFilter();
        if (StringHelper.isNotEmpty(displayName)) {
            filter.setDisplayName(displayName);
        }
        filter.orderBy("create_Time").order(DESC);
        snakerEngineFacets.getEngine().process().getProcesss(page, filter);
        return PageResponse.ok(JSONUtil.parse(page.getResult()), page.getTotalCount());
    }

    /**
     * 根据流程定义ID，删除流程定义
     *
     * @param id
     */
    @ApiOperation(value = "根据流程定义ID，删除流程定义", tags = "流程引擎-流程")
    @RequestMapping(value = "/process/delete/{id}", method = RequestMethod.GET)
    @Metrics
    @SaCheckPermission("flow.delete")
    public Response<Void> processDelete(@PathVariable("id") String id) {
        snakerEngineFacets.getEngine().process().undeploy(id);
        return Response.ok();
    }

    /**
     * 保存流程定义[web流程设计器]
     *
     * @param model
     */
    @ApiOperation(value = "保存流程定义[web流程设计器]", tags = "流程引擎-流程")
    @RequestMapping(value = "/process/deployXml", method = RequestMethod.POST)
    @SaCheckPermission("flow.update")
    @RepeatSubmitLimit(businessKey = "savemodel", businessParam = "#model")
    public boolean saveProcessXml(String model, String id,
                                  @RequestParam(required = false, defaultValue = "false") boolean xmlHearder) {
        InputStream input = null;
        try {
            String xml = "";
            if (!xmlHearder) {
                xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
            }
            xml = xml + SnakerHelper.convertXml(model);
            input = StreamHelper.getStreamFromString(xml);
            if (StringUtils.isNotEmpty(id)) {
                snakerEngineFacets.getEngine().process().redeploy(id, input);
            } else {
                snakerEngineFacets.getEngine().process().deploy(input);
            }
        } catch (Exception e) {
            log.error("", e);
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {
                }
            }
        }
        return true;
    }

    @ApiOperation(value = "流程定义+流程状态", tags = "流程引擎-流程")
    @RequestMapping(value = "/process/json", method = RequestMethod.GET)
    @Metrics
    public Object getProcessJson(String processId, String orderId) {
        if (StrUtil.isBlank(processId)) {
            processId = snakerEngineFacets.getEngine().query().getHistOrder(orderId).getProcessId();
        }
        Process process = snakerEngineFacets.getEngine().process().getProcessById(processId);
        AssertHelper.notNull(process);
        ProcessModel model = process.getModel();
        Map<String, String> jsonMap = new HashMap<>();
        if (model != null) {
            jsonMap.put("process", SnakerHelper.getModelJson(model));
        }

        if (StringUtils.isNotEmpty(orderId)) {
            List<Task> tasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
            List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
            jsonMap.put("state", SnakerHelper.getStateJson(model, tasks, historyTasks));
        }
        return jsonMap;
    }

    // --------- 任务相关 --------- //


    /**
     * 根据当前用户查询待办任务列表
     */
    @GetMapping("/task/todoList")
    @ApiOperation(value = "根据当前用户查询待办任务列表", tags = "流程引擎-任务")
    public PageResponse<List<WorkItem>> getUserTaskTodoList() {
        Page<WorkItem> page = new Page<>(30);
        snakerEngineFacets.getEngine().query().getWorkItems(page,
                new QueryFilter().setOperator(StpUtil.getLoginIdAsString()));
        return PageResponse.ok(page.getResult(), page.getTotalCount());
    }

    /**
     * 根据当前用户查询待办任务列表
     */
    @GetMapping("/task/doneList")
    @ApiOperation(value = "根据当前用户查询已办任务列表", tags = "流程引擎-任务")
    public PageResponse<List<WorkItem>> getUserTaskDoneList() {
        Page<WorkItem> page = new Page<>(30);
        snakerEngineFacets.getEngine().query().getHistoryWorkItems(page,
                new QueryFilter().setOperator(StpUtil.getLoginIdAsString()));
        return PageResponse.ok(page.getResult(), page.getTotalCount());
    }

    @GetMapping("/task/actor/add")
    @ApiOperation(value = "根据流程实例id和任务名称，动态增加任务参与者", tags = "流程引擎-任务")
    public Response<Void> addTaskActor(String orderId, String taskName, String operator) {
        List<Task> tasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName) && StringUtils.isNotEmpty(operator)) {
                snakerEngineFacets.getEngine().task().addTaskActor(task.getId(), operator);
            }
        }
        return Response.ok();
    }

    @GetMapping("/task/tip")
    @ApiOperation(value = "根据流程实例id和任务名称,查找当前任务的到达时间和待执行人", tags = "流程引擎-任务")
    public Response<Map<String, String>> taskTip(String orderId, String taskName) {
        List<Task> tasks = snakerEngineFacets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        StringBuilder builder = new StringBuilder();
        String createTime = StrUtil.EMPTY;
        String finishTime = StrUtil.EMPTY;
        boolean find = false;
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                String[] actors = snakerEngineFacets.getEngine().query().getTaskActorsByTaskId(task.getId());
                for (String actor : actors) {
                    SysUser sysUser = sysUserService.getById(Long.valueOf(actor));
                    String nickName = sysUser.getNickName();
                    builder.append(nickName).append(",");
                    find = true;
                }
                createTime = task.getCreateTime();
            }
        }
        if (!find) {
            List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query().getHistoryTasks(new QueryFilter().setOrderId(orderId));
            for (HistoryTask task : historyTasks) {
                if (task.getTaskName().equalsIgnoreCase(taskName)) {
                    String[] actors = snakerEngineFacets.getEngine().query().getHistoryTaskActorsByTaskId(task.getId());
                    for (String actor : actors) {
                        SysUser sysUser = sysUserService.getById(Long.valueOf(actor));
                        String nickName = sysUser.getNickName();
                        builder.append(nickName).append(",");
                    }
                    createTime = task.getCreateTime();
                    finishTime = task.getFinishTime();
                }
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        Map<String, String> data = new HashMap<>();
        data.put("actors", builder.toString());
        data.put("createTime", createTime);
        data.put("finishTime", finishTime);
        return Response.ok(data);
    }

    /**
     * 活动任务的驳回
     */
    @GetMapping("/task/reject")
    @ApiOperation(value = "\t 【审批任务】驳回，根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点\n" +
            "\t 1、nodeName为null时，则跳转至上一步处理\n" +
            "\t 2、nodeName不为null时，则任意跳转，即动态创建转移", tags = "流程引擎-任务")
    public Response<Void> activeTaskReject(String taskId, String nodeName, String reason) {
        Dict rejectReason = Dict.create()
                // 拒绝原因，建议单独搞个 审核表 审核的comment file单独存储
                .set("rejectReason", reason);
        snakerEngineFacets.executeAndJump(taskId, StpUtil.getLoginIdAsString(), rejectReason, nodeName);
        return Response.ok();
    }

    /**
     * 活动任务的驳回-驳回到发起人节点
     */
    @GetMapping("/task/rejectToCreate")
    @ApiOperation(value = "任务的驳回-驳回到发起人节点", tags = "流程引擎-任务")
    public Response<Void> doRejectToCreate(String taskId) {
        List<WorkItem> workItems = snakerEngineFacets.getEngine().query().getWorkItems(null, new QueryFilter().setTaskId(taskId));
        if (CollUtil.isEmpty(workItems)) {
            Response.error500("不存在的任务喽");
        }
        WorkItem workItem = workItems.get(0);
        Process process = snakerEngineFacets.getEngine().process().getProcessById(workItem.getProcessId());
        ProcessModel model = process.getModel();
        // 获取开始节点下面的第一个节点
        String name = model.getStart().getOutputs().get(0).getTarget().getName();
        snakerEngineFacets.executeAndJump(taskId, StpUtil.getLoginIdAsString(), null, name);
        return Response.ok();
    }

    @RequestMapping(value = "/task/approval", method = RequestMethod.GET)
    @ApiOperation(value = "【审批任务】同意", tags = "流程引擎-任务")
    public Response<Void> doApproval(String taskId, String reason) {
        snakerEngineFacets.execute(taskId, StpUtil.getLoginIdAsString(), null);
        return Response.ok();
    }


    /**
     * 历史任务撤回，这玩意只能撤回刚发出的且没有被处理的
     *
     * @param taskId
     * @return
     */
    @GetMapping("/task/undo")
    @ApiOperation(value = "根据任务主键id、操作人撤回任务", tags = "流程引擎-任务")
    public Response<Void> historyTaskUndo(String taskId) {
        snakerEngineFacets.getEngine().task().withdrawTask(taskId, StpUtil.getLoginIdAsString());
        return Response.ok();
    }

    @GetMapping("/task/transferMajor")
    @ApiOperation(value = "转办", tags = "流程引擎-任务")
    public Response<Void> transferMajor(String taskId, String nextOperator) {
        snakerEngineFacets.transferMajor(taskId, StpUtil.getLoginIdAsString(), nextOperator.split(","));
        return Response.ok();
    }


    //  ------------------ 流程实例管理 ------------------ //

    /**
     * 流程实例管理
     */
    @ApiOperation(value = "流程分页查询", tags = "流程引擎-流程实例")
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public Response orderList(Page<HistoryOrder> page, String displayName) {
        QueryFilter filter = new QueryFilter();
        if (StringHelper.isNotEmpty(displayName)) {
            filter.setDisplayName(displayName);
        }
        filter.orderBy("create_Time").order(DESC);
        snakerEngineFacets.getEngine().query().getHistoryOrders(page, filter);
        return PageResponse.ok(JSONUtil.parse(page.getResult()), page.getTotalCount());
    }
}
