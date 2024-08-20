package com.laker.admin.integration;

import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.pojo.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 集成测试
 *
 * @SpringBootTest用于加载完整的Spring上下文，进行端到端的集成测试。
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto();
        ResponseEntity<Response> response = restTemplate.postForEntity("/sys/auth/v1/login", loginDto, Response.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("", response.getBody().getMsg());
    }
}
