package com.laker.admin.framework.localmessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@EasyLocalMessageOperation(name = "example")
@Slf4j
public class ExampleILocalMessageOperation implements ILocalMessageOperation {

    @Autowired
    private EasyLocalMessageTemplate easyLocalMessageTemplate;

    @Transactional
    public void exampleMethod1(String param) {
        // 模拟本地数据操作
        System.out.println("Performing local operation with param: " + param);
        localOperation(Map.of("param", param));
        // 模拟远程调用或者耗时操作
        System.out.println("Performing remote operation");
        remoteOperation(Map.of("param", param));
    }

    // 这个方法使用本地消息表实现消息的最终一致性
    public void test(String param) {
        Map<String, Object> paramMap = Map.of("param", param);
        easyLocalMessageTemplate.execute(this, paramMap);
    }

    @Override
    public boolean localOperation(Map<String, Object> params) {
        log.info("Performing local operation with params: {}", params);
        return true;
    }

    @Override
    public boolean remoteOperation(Map<String, Object> params) {
        log.info("Performing remote operation with params: {}", params);
        return false;
    }
}