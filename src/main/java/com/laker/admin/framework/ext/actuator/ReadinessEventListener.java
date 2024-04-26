package com.laker.admin.framework.ext.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 注册事件监听器，以便在应用程序可用性状态发生变化时收到通知
 * </p>
 * 通过发布AvailabilityChangeEvent 事件来更新应用程序状态
 * AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);
 * AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
 */
@Slf4j
@Component
public class ReadinessEventListener {

    /**
     * 默认 EventPublishingRunListener 会发布 AvailabilityChangeEvent 事件
     *
     * @see EventPublishingRunListener
     * <p>
     * running() -> ACCEPTING_TRAFFIC
     * - AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC);
     */
    @EventListener
    public void onEvent(AvailabilityChangeEvent<ReadinessState> event) {
        switch (event.getState()) {
            case ACCEPTING_TRAFFIC:
                // notify others
                log.warn("readiness is accepting traffic");
                break;
            case REFUSING_TRAFFIC:
                // we're back
                log.warn("readiness is refusing traffic");
        }
    }
}