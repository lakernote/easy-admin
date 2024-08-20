package com.laker.admin.module.ext.service;

import com.laker.admin.module.ext.mapper.ExtLogMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 单元测试
 * 单元测试主要测试单个方法或类的功能，通常不涉及Spring的上下文。通过Mock对象来隔离依赖。
 * <p>
 *
 * @ExtendWith(MockitoExtension.class)是JUnit5中的一个注解，用于启用Mockito的注解支持，如@Mock和@InjectMocks等。
 * 当你在测试类上使用这个注解时，Mockito会自动处理你的测试类中的注解，并为你创建和注入mock对象。
 * 这意味着你不需要在每个测试方法中手动创建mock对象，也不需要手动调用MockitoAnnotations.initMocks(this)。
 * 这使得测试代码更加简洁，更容易阅读和维护。
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @Mock
    private ExtLogMapper extLogMapper;

    @InjectMocks
    private DemoService demoService;

    @Test
    void test1() {
        // 模拟方法返回值
        when(extLogMapper.selectDistinctIp()).thenReturn(1);

        // 调用方法
        demoService.test();

        // 验证
         verify(extLogMapper).selectDistinctIp();
    }
}