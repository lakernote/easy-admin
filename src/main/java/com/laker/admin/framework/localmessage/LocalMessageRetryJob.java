package com.laker.admin.framework.localmessage;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.laker.admin.framework.localmessage.entity.LocalMessage;
import com.laker.admin.framework.localmessage.mapper.LocalMessageMapper;
import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EasyJob(taskCode = "", taskName = "", cron = "0 0/1 * * * ?") // 每分钟执行一次
@Slf4j
public class LocalMessageRetryJob implements IEasyJob {

    @Autowired
    private LocalMessageMapper localMessageMapper;

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, ILocalMessageOperation> beansWithName = MapUtil.newHashMap();

    @PostConstruct
    public void init() {
        // 初始化方法
        log.info("LocalMessageRetryJob initialized");
        final Map<String, ILocalMessageOperation> beansWithAnnotation = applicationContext.getBeansOfType(ILocalMessageOperation.class);
        // 遍历所有带有@EasyLocalMessageOperation注解的类 把@EasyLocalMessageOperation中name属性的值作为key，组装成map
        for (Map.Entry<String, ILocalMessageOperation> entry : beansWithAnnotation.entrySet()) {
            // 获取@EasyLocalMessageOperation注解
            EasyLocalMessageOperation annotation = AnnotationUtils.findAnnotation(entry.getValue().getClass(), EasyLocalMessageOperation.class);
            if (annotation != null) {
                String name = annotation.name();
                if (beansWithName.containsKey(name)) {
                    throw new RuntimeException("Duplicate bean name: " + name);
                }
                beansWithName.put(name, entry.getValue());
                log.info("Found bean with name: {} and class: {}", name, entry.getValue().getClass().getName());
            }
        }
    }


    @Override
    public void execute(Map map) throws Exception {
        // TODO 这里要实现每台机器上抓取一部分数据进行处理，提高性能
        List<LocalMessage> failedMessages = localMessageMapper.findByStatus("FAILED");
        if (failedMessages == null || failedMessages.isEmpty()) {
            log.info("No failed messages to process");
            return;
        }
        for (LocalMessage localMessage : failedMessages) {
            final ILocalMessageOperation bean = beansWithName.get(localMessage.getName());
            if (bean == null) {
                log.error("No bean found for name: {}", localMessage.getName());
                continue;
            }
            // 获取注解
            // 获取目标类
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            // 从目标类获取注解
            EasyLocalMessageOperation easyLocalMessageOperation = AnnotationUtils.findAnnotation(targetClass, EasyLocalMessageOperation.class);

            if (easyLocalMessageOperation == null) {
                log.error("No EasyLocalMessageOperation annotation found for bean: {}", bean.getClass().getName());
                continue;
            }
            if (localMessage.getRetryCount() < easyLocalMessageOperation.maxRetryCount()) {
                try {
                    final boolean remoteOperation = bean.remoteOperation(JSONUtil.parseObj(localMessage.getParam()));
                    if (remoteOperation) {
                        // 远程操作成功，更新本地消息状态
                        localMessage.setStatus("SENT");
                    } else {
                        // 远程操作失败，更新本地消息状态
                        localMessage.setStatus("FAILED");
                        localMessage.setRetryCount(localMessage.getRetryCount() + 1);
                    }
                    localMessage.setUpdateTime(new Date());
                    localMessageMapper.updateById(localMessage);
                    log.info("Message sent successfully: {}", localMessage.getName());
                } catch (Exception e) {
                    localMessage.setRetryCount(localMessage.getRetryCount() + 1);
                    localMessage.setUpdateTime(new Date());
                    localMessageMapper.updateById(localMessage);
                    log.error("Failed to send message: {}. Retry count: {}", localMessage.getName(), localMessage.getRetryCount(), e);
                }
            } else {
                // 达到最大重试次数，进行人工处理
                localMessage.setStatus("ERROR");
                localMessage.setUpdateTime(new Date());
                localMessageMapper.updateById(localMessage);
                // 记录日志或发送通知
                // 人工处理
                log.error("Message failed after max retries: {}", localMessage.getName());
            }
        }
    }
}