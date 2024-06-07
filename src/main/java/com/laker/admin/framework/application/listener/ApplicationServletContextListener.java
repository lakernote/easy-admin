package com.laker.admin.framework.application.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ApplicationServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("ApplicationServletContextListener ServletContextEvent Initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        LOGGER.info("ApplicationServletContextListener ServletContextEvent Destroyed");
    }

}