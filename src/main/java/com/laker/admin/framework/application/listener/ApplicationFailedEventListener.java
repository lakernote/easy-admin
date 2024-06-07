package com.laker.admin.framework.application.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationFailedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        if (event.getException() != null) {
            LOGGER.error("Spring Boot application exception occurred!");
            LOGGER.error(event.getException().getMessage());
            LOGGER.error("",event.getException());
            event.getApplicationContext().close();
            System.exit(-1);
        }
    }
}