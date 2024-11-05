package com.laker.admin.framework.handler;

import com.laker.admin.module.remote.feign.EasyTodoClient;
import com.laker.admin.module.remote.feign.IpifyClient;
import com.laker.admin.module.remote.feign.dto.IpifyVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;

import static org.mockito.Mockito.*;

/**
 * 单元测试
 * 单元测试主要测试单个方法或类的功能，通常不涉及Spring的上下文。通过Mock对象来隔离依赖。
 * 单元测试主要用于测试应用程序中的单个功能或方法。Spring Boot通常使用JUnit和Mockito来编写单元测试。
 */
class ExternalIpHandlerTest {

    @Mock
    private IpifyClient ipifyClient;

    @Mock
    private EasyTodoClient easyTodoClient;

    @InjectMocks
    private ExternalIpHandler externalIpHandler;

    @BeforeEach
    void setUp() {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLogExternalIpAndTodoWhenRun() {
        // Setup 打桩模拟行为
        IpifyVo ipifyVo = new IpifyVo();
        ipifyVo.setIp("192.168.0.1");
        when(ipifyClient.getIpAddress()).thenReturn(ipifyVo);
        when(easyTodoClient.getById(any(URI.class), eq(1))).thenReturn("Test Todo");

        // Act 执行测试
        externalIpHandler.run();

        // Verify 验证行为
        verify(ipifyClient, times(1)).getIpAddress();
        verify(easyTodoClient, times(1)).getById(any(URI.class), eq(1));
    }
}