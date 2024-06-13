package com.laker.admin.config.UReport;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ConditionalOnWebApplication
@ImportResource({"classpath:ureport-console-context.xml"})
@PropertySource({"classpath:ureport.properties"})
public class UReportConfig {
    @Bean
    @ConditionalOnMissingBean
    public PropertySourcesPlaceholderConfigurer propertySourceLoader() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setOrder(1);
        return configurer;
    }

    @Bean
    public ServletRegistrationBean<UReportServlet> registerUReportServlet() {
        return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
    }
}
