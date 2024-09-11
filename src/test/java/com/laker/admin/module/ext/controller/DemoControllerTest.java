package com.laker.admin.module.ext.controller;

import com.laker.admin.config.EasyAdminConfig;
import com.laker.admin.module.remote.IpifyClient;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MVC测试（@WebMvcTest）
 *
 * @WebMvcTest用于测试Spring MVC层，如Controller层，不加载完整的Spring上下文。
 * @WebMvcTest默认只加载与Spring MVC相关的组件，而不加载其他的Spring上下文配置或Bean。
 * 因此，如果你的DemoController依赖于某些其他Bean（如EasyAdminConfig），这些Bean在测试环境中要mock加载。
 */
@WebMvcTest(DemoController.class)
@Import({EasyAdminConfig.class})  // 仅导入必要的配置
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // MockBean注解用于创建一个模拟对象，用于替换Spring上下文中的真实Bean。
    @MockBean
    private IpifyClient ipifyClient;
    @MockBean
    private ObservationRegistry observationRegistry;
    // 以上是启动的时候需要的bean

    @MockBean
    private CacheManager cacheManager;
    @MockBean
    private Cache myCache;

//    @Test
    void getFromRedis() throws Exception {

        // Mocking the Cache behavior
        when(cacheManager.getCache("myCache")).thenReturn(myCache);
        when(myCache.get("key", String.class)).thenReturn("mockedValue");

        mockMvc.perform(get("/demo/v1/redis-get"))
                .andExpect(status().isOk());

    }
}