package com.laker.admin.framework.application.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        if (event.getException() != null) {
            log.error("[启动异常,退出]", event.getException());
            event.getApplicationContext().close();
            System.exit(-1);
        }
    }
}