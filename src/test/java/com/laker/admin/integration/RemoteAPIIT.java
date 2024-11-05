package com.laker.admin.integration;

import com.laker.admin.module.remote.rest.HttpBinClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 集成测试-测试kafka
 *
 * @SpringBootTest用于加载完整的Spring上下文，进行端到端的集成测试。
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@ActiveProfiles("test")
class RemoteAPIIT {

    @Autowired
    HttpBinClient httpBinClient;

    @Test
    void tetHttpBinClient() {
        String testMessage = "Hello";
        final String s = httpBinClient.get(testMessage);
        log.info("result: {}", s);
    }
}