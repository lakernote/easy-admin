package com.laker.admin.framework.handler;

import com.laker.admin.module.remote.EasyTodoClient;
import com.laker.admin.module.remote.IpifyClient;
import com.laker.admin.module.remote.dto.IpifyVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;

import static org.mockito.Mockito.*;

class ExternalIpHandlerTest {

    @Mock
    private IpifyClient ipifyClient;

    @Mock
    private EasyTodoClient easyTodoClient;

    @InjectMocks
    private ExternalIpHandler externalIpHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLogExternalIpAndTodoWhenRun() {
        // Setup
        IpifyVo ipifyVo = new IpifyVo();
        ipifyVo.setIp("192.168.0.1");
        when(ipifyClient.getIpAddress()).thenReturn(ipifyVo);
        when(easyTodoClient.getById(any(URI.class), eq(1))).thenReturn("Test Todo");

        // Act
        externalIpHandler.run();

        // Verify
        verify(ipifyClient, times(1)).getIpAddress();
        verify(easyTodoClient, times(1)).getById(any(URI.class), eq(1));
    }
}