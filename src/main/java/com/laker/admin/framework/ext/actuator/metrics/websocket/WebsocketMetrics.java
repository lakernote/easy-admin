package com.laker.admin.framework.ext.actuator.metrics.websocket;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author laker
 */
public class WebsocketMetrics {
    private final MeterRegistry meterRegistry;
    private final Timer lifeTimeTimer;
    private final Counter websocketOpenCounter;
    private final Counter transportErrorCounter;

    public WebsocketMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        // 初始化一些 tag 值是死的埋点
        lifeTimeTimer = Timer.builder("connection.lifetime").tag("tag1", "value1").tag("tag2", "value1").register(meterRegistry);
        websocketOpenCounter = Counter.builder("connection.open.count").tag("tag1", "value1").tag("tag2", "value1").register(meterRegistry);
        transportErrorCounter = Counter.builder("connection.transport.error.count").tag("tag1", "value1").register(meterRegistry);
    }

    public void websocketOpenCounter() {
        websocketOpenCounter.increment(1);
    }

    public void websocketGauge(Map webSocketSessionMap) {
        Gauge.builder("connection.current.gauge", () -> webSocketSessionMap.size()).register(meterRegistry);
    }

    /**
     * tag 值是活的可以这样埋点
     */

    public void webSocketCloseConnectionCounter(long count, int closeCode) {
        Counter webSocketCloseConnectionCounter = Counter.builder("connection.close.count")
                .tag("code", closeCode + "")
                .register(meterRegistry);
        webSocketCloseConnectionCounter.increment(count);
    }


    public void websocketProcessTimer(long duration, String exception) {

        Timer wsOpenFailureTimer = Timer.builder("connection.message.process")
                .tag("tag1", "value1")
                .tag("exception", exception).register(meterRegistry);
        wsOpenFailureTimer.record(duration, TimeUnit.NANOSECONDS);
    }

}

