package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.laker.admin.framework.cache.ICache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 启动了完整的 Spring 应用程序上下文，但没有服务器。
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SysUserController_APITest {
    /**
     * 会走 filter controller，这个比较仿真
     */
    @Autowired
    private MockMvc mvc;

    @MockBean
    ICache iCache;

    /**
     * 不需要user的测试
     *
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception {
        mvc.perform(get("/captcha"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("data:image/png;")));
    }

    /**
     * 需要user的测试
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        StpUtil.login("1");
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        mvc.perform(get("/sys/user/getAll").header(tokenInfo.getTokenName(), tokenInfo.getTokenValue()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
