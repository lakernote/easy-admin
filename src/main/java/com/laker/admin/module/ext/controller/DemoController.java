package com.laker.admin.module.ext.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequest;
import com.laker.admin.framework.kafka.EasyKafkaConfig;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.enums.DemoTypeEnum;
import com.laker.admin.module.enums.Distance;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.service.IExtLogService;
import com.laker.admin.module.ext.vo.qo.City;
import com.laker.admin.module.remote.feign.IpifyClient;
import com.laker.admin.module.remote.feign.dto.IpifyVo;
import com.laker.admin.module.sys.entity.SysUser;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    final IpifyClient ipifyClient;
    final KafkaTemplate<String, String> kafkaTemplate;
    final EasyKafkaConfig easyKafkaConfig;
    final CacheManager cacheManager;
    final ObservationRegistry registry;
    final IExtLogService extLogService;

    public DemoController(IpifyClient ipifyClient, ObservationRegistry registry,
                          @Autowired(required = false) KafkaTemplate<String, String> kafkaTemplate,
                          @Autowired(required = false) EasyKafkaConfig easyKafkaConfig,
                          @Autowired(required = false) CacheManager cacheManager, IExtLogService extLogService) {
        this.ipifyClient = ipifyClient;
        this.registry = registry;
        this.kafkaTemplate = kafkaTemplate;
        this.easyKafkaConfig = easyKafkaConfig;
        this.cacheManager = cacheManager;
        this.extLogService = extLogService;
    }


    @GetMapping(path = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "0.pageList Param+Header+Path 参数数组 - querystring")
    @Parameters({
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", example = "xxxx", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "page", description = "第几页", in = ParameterIn.QUERY),
            @Parameter(name = "limit", description = "每页多少记录", in = ParameterIn.QUERY),
            @Parameter(name = "types", description = "类型", in = ParameterIn.QUERY)
    })
    public Response<SysUser> pageList(
            @PathVariable("id") Long id,
            @RequestHeader("token") String token,
            @RequestParam(required = false, defaultValue = "1") long page,
            @RequestParam(required = false, defaultValue = "10") long limit,
            @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info("id:{},token:{},page:{},limit:{},types:{}", id, token, page, limit, types);
        SysUser sysUser = new SysUser();
        sysUser.setUserId(2334234234324324343L);
        sysUser.setCreateTime(LocalDateTime.now());
        return Response.ok(sysUser);
    }

    @GetMapping("/enum")
    @Operation(summary = "1.参数枚举 - querystring")
    public void paramEnum(Distance distance) {
        log.info(distance.toString());
    }

    @GetMapping("/Object")
    @Operation(summary = "2.参数实体 - querystring")
    public void paramObject(City city) {
        log.info(city.toString());
    }

    @PostMapping("/Object-Json")
    @Operation(summary = "3.参数实体 - json")
    public City paramObjectJson(@RequestBody City city) {
        log.info(city.toString());
        return city;
    }

    @PostMapping("/CurrentUser")
    @Operation(summary = "4.实体+CurrentUser - json")
    public void paramCurrentUser(@RequestBody City city, CurrentUser user) {
        log.info(user.toString());
    }

    /**
     * /api/products?page=1&size=2&sort=rank|asc,zipCode|desc&filter=city|eq|beijing
     * /api/products?page=1&size=2&sort=rank|asc,zipCode|desc&filter=city|eq|beijing,zipCode|eq|100000
     * ?page=1&size=2&sort=cost|asc&filter=status|eq|1
     *
     * @param request
     * @return
     */
    @GetMapping("/PageRequest")
    @Operation(summary = "5.参数 PageRequest")
    public Response paramPageRequest(PageRequest request) {
        log.info(request.getQueryWrapper().getTargetSql());
        final Page<ExtLog> pages = extLogService.page(request.toPage(), request.getQueryWrapper());
        return Response.ok(pages);
    }

    @GetMapping("/remote-call")
    @Operation(summary = "6.远程调用-获取ip地址")
    public Response<IpifyVo> remoteCall(
            @Parameter(description = "traceId,用于链路追踪", example = "67614c197f54471795cc84a3a073dd25", required = true, in = ParameterIn.HEADER)
            @RequestHeader String traceId) {
        List<String> lowCardinalityValues = Arrays.asList("userType1", "userType2", "userType3"); // Simulates low number of values
        // 用 Micrometer 改进 metrics，并通过 Micrometer tracing （以前称为 Spring Cloud Sleuth）提供新的分布式 tracing 支持。最值得注意的变化是，
        // 它将包含对 log 关联的内置支持，W3C上下文传递将是默认传播类型，我们将支持自动传播元数据，以供 tracing 基础设施(称为“远程包裹”)使用，帮助标记观察结果。
        // 最重要的变化是我们引入了一个新的API：Observation API。
        // 它创建理念是，希望用户使用单一 API 就能检测代码，并从中获得多种信息（例如 metrics, tracing, logging）。
        // classpath 添加了 Micrometer Tracing
        return Observation.createNotStarted("my.observation", registry)
                // Low cardinality means that the number of potential values won't be big. Low cardinality entries will end up in e.g. Metrics
                .lowCardinalityKeyValue("userType", "userType1")
                // High cardinality means that the number of potential values can be large. High cardinality entries will end up in e.g. Spans
                .highCardinalityKeyValue("userId", "highCardinalityUserId")
                // <command-line-runner> is a "contextual" name that gives more details within the provided context. It will be used to name e.g. Spans
                .contextualName("command-line-runner")
                // The following lambda will be executed with an observation scope (e.g. all the MDC entries will be populated with tracing information). Also the observation will be started, stopped and if an error occurred it will be recorded on the observation
                .observe(() -> {
                    log.info("Will send a request to the server"); // Since we're in an observation scope - this log line will contain tracing MDC entries ...
                    IpifyVo ipAddress = ipifyClient.getIpAddress();
                    log.info("Got response [{}]", ipAddress); // ... so will this line
                    return Response.ok(ipifyClient.getIpAddress());
                });


    }

    @GetMapping("/sendMessage")
    @Operation(summary = "7.使用kafka发送消息")
    public void sendMessage(String message) {
        // 阻塞发送线程并获取有关已发送消息的结果，我们可以调用CompletableFuture对象的get API  。线程将等待结果，但这会减慢生产者的速度。
        //Kafka 是一个快速流处理平台。因此，最好以异步方式处理结果，以便后续消息不会等待上一条消息的结果。通过回调来实现
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(easyKafkaConfig.getTopic(), 0, "key-key", message);
        // SendResult<String, String> result = future.get();
        // LOG.info("Partition: {}", result.getRecordMetadata().partition());
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
                future.complete(result);
            } else {
                log.info("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
                future.completeExceptionally(ex);
            }
        });
    }

    @GetMapping("/redis-get")
    @Operation(summary = "8.查询redis")
    public void getFromRedis() {
        final Cache myCache = cacheManager.getCache("myCache");
        assert myCache != null;
        final String key = myCache.get("key", String.class);
        log.info("key:{}", key);
        myCache.put("key", "value");
        final String key1 = myCache.get("key", String.class);
        log.info("key1:{}", key1);
    }

    @GetMapping("/getResponseEntity")
    @Operation(summary = "8.getResponseEntity")
    public ResponseEntity getResponseEntity() {
        if (RandomUtil.randomBoolean()) {
            return ResponseEntity.ok(List.of(1, 2, 3));
        }
        Dict dict = Dict.create()
                .set("name", "laker")
                .set("createTime", LocalDateTime.now())
                .set("longArr", List.of(1L, 2L, 3L))
                .set("age", 18);
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(dict);
    }
}