package com.laker.admin.integration;

import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.pojo.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 集成测试-API集成测试
 *
 * @SpringBootTest用于加载完整的Spring上下文，进行端到端的集成测试。 可以理解为是一个正常启动的Spring Boot应用。
 * 数据库，缓存，消息队列等都会启动。
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class APIIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto();
        ResponseEntity<Response> response = restTemplate.postForEntity("/sys/auth/v1/login", loginDto, Response.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testHelloWorld() throws Exception {
        // 发送 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/sys/auth/v1/login")
                        // 设置请求头
                        .accept(MediaType.APPLICATION_JSON)
                        // 设置请求体
                        .contentType(MediaType.APPLICATION_JSON)
                        // 设置请求体内容
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                // 验证响应状态码
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 验证响应内容
                .andExpect(MockMvcResultMatchers.content().string("Hello, World!"));
    }
}
