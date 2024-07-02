package com.laker.admin.framework.handler;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OpenBrowserHandlerTest {

    @InjectMocks
    private OpenBrowserHandler openBrowserHandler;

    @BeforeEach
    void setUp() {
        openBrowserHandler = new OpenBrowserHandler();
        ReflectionTestUtils.setField(openBrowserHandler, "serverPort", 8080);
    }

    @Test
    void shouldOpenBrowserOnWindows() throws IOException {
        try (MockedStatic<SystemUtil> utilities = Mockito.mockStatic(SystemUtil.class)) {
            OsInfo osInfo = mock(OsInfo.class);
            when(osInfo.isWindows()).thenReturn(true);
            utilities.when(SystemUtil::getOsInfo).thenReturn(osInfo);

            try (MockedStatic<Runtime> runtime = Mockito.mockStatic(Runtime.class)) {
                Runtime mockRuntime = mock(Runtime.class);
                runtime.when(Runtime::getRuntime).thenReturn(mockRuntime);

                openBrowserHandler.run();

                verify(mockRuntime, times(1)).exec(any(String.class));
            }
        }
    }

    @Test
    void shouldNotOpenBrowserOnNonWindows() throws IOException {
        try (MockedStatic<SystemUtil> utilities = Mockito.mockStatic(SystemUtil.class)) {
            OsInfo osInfo = mock(OsInfo.class);
            when(osInfo.isWindows()).thenReturn(false);
            utilities.when(SystemUtil::getOsInfo).thenReturn(osInfo);

            try (MockedStatic<Runtime> runtime = Mockito.mockStatic(Runtime.class)) {
                Runtime mockRuntime = mock(Runtime.class);
                runtime.when(Runtime::getRuntime).thenReturn(mockRuntime);

                openBrowserHandler.run();

                verify(mockRuntime, times(0)).exec(any(String.class));
            }
        }
    }
}