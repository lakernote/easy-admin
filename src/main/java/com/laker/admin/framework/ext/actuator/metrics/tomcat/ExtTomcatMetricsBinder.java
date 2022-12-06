package com.laker.admin.framework.ext.actuator.metrics.tomcat;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.Collections;

/**
 * @author: laker
 * @date: 2022/12/6
 **/
public class ExtTomcatMetricsBinder implements ApplicationListener<ApplicationStartedEvent>, DisposableBean {

    private final MeterRegistry meterRegistry;

    private final Iterable<Tag> tags;

    private volatile ExtTomcatMetrics tomcatMetrics;

    public ExtTomcatMetricsBinder(MeterRegistry meterRegistry) {
        this(meterRegistry, Collections.emptyList());
    }

    public ExtTomcatMetricsBinder(MeterRegistry meterRegistry, Iterable<Tag> tags) {
        this.meterRegistry = meterRegistry;
        this.tags = tags;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Manager manager = findManager(applicationContext);
        this.tomcatMetrics = new ExtTomcatMetrics(manager, this.tags);
        this.tomcatMetrics.bindTo(this.meterRegistry);
    }

    private Manager findManager(ApplicationContext applicationContext) {
        if (applicationContext instanceof WebServerApplicationContext) {
            WebServer webServer = ((WebServerApplicationContext) applicationContext).getWebServer();
            if (webServer instanceof TomcatWebServer) {
                Context context = findContext((TomcatWebServer) webServer);
                return context.getManager();
            }
        }
        return null;
    }

    private Context findContext(TomcatWebServer tomcatWebServer) {
        for (Container container : tomcatWebServer.getTomcat().getHost().findChildren()) {
            if (container instanceof Context) {
                return (Context) container;
            }
        }
        return null;
    }

    @Override
    public void destroy() {
        if (this.tomcatMetrics != null) {
            this.tomcatMetrics.close();
        }
    }

}
