package com.laker.admin.framework.localmessage;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.laker.admin.framework.localmessage.mapper.LocalMessageMapper;
import com.laker.admin.module.task.core.EasyJob;
import com.laker.admin.module.task.core.IEasyJob;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EasyJob(taskCode = "", taskName = "", cron = "0 0/1 * * * ?") // 每分钟执行一次
public class LocalMessageRetryJob implements IEasyJob {

    @Autowired
    private LocalMessageMapper localMessageMapper;

    @Autowired
    private ApplicationContext applicationContext;

    private static final int MAX_RETRY_COUNT = 3;

    private Map<String, ILocalMessageOperation> beansWithName = MapUtil.newHashMap();

    @PostConstruct
    public void init() {
        // 初始化方法
        System.out.println("LocalMessageRetryJob initialized");
        final Map<String, ILocalMessageOperation> beansWithAnnotation = applicationContext.getBeansOfType(ILocalMessageOperation.class);
        // 遍历所有带有@EasyLocalMessageOperation注解的类 把@EasyLocalMessageOperation中name属性的值作为key，组装成map
        for (Map.Entry<String, ILocalMessageOperation> entry : beansWithAnnotation.entrySet()) {
            // 获取@EasyLocalMessageOperation注解
            EasyLocalMessageOperation annotation = AnnotationUtils.findAnnotation(entry.getValue().getClass(), EasyLocalMessageOperation.class);
            if (annotation != null) {
                String name = annotation.name();
                System.out.println("Found bean with EasyLocalMessageOperation annotation: " + name);
                beansWithName.put(name, entry.getValue());
            }
        }
    }


    @Override
    public void execute(Map map) throws Exception {
        List<LocalMessage> failedMessages = localMessageMapper.findByStatus("FAILED");
        for (LocalMessage localMessage : failedMessages) {
            if (localMessage.getRetryCount() < MAX_RETRY_COUNT) {
                try {
                    final ILocalMessageOperation bean = beansWithName.get(localMessage.getName());
                    bean.remoteOperation(JSONUtil.parseObj(localMessage.getParam()));
                    localMessage.setStatus("SENT");
                    localMessage.setUpdateTime(new Date());
                    localMessageMapper.updateById(localMessage);
                    System.out.println("Retry success for message: " + localMessage.getName());
                } catch (Exception e) {
                    localMessage.setRetryCount(localMessage.getRetryCount() + 1);
                    localMessage.setUpdateTime(new Date());
                    localMessageMapper.updateById(localMessage);
                    System.out.println("Retry failed for message: " + localMessage.getName() + ", Error: " + e.getMessage());
                }
            } else {
                // 达到最大重试次数，进行人工处理
                localMessage.setStatus("ERROR");
                localMessage.setUpdateTime(new Date());
                localMessageMapper.updateById(localMessage);
                // 记录日志或发送通知
                // 人工处理
                System.out.println("Max retry count reached for message: " + localMessage.getName());
            }
        }
    }
}