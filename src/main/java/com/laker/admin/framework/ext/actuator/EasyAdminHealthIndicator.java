package com.laker.admin.framework.ext.actuator;

import cn.hutool.core.util.RandomUtil;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class EasyAdminHealthIndicator extends AbstractHealthIndicator {

    @Override
    public void doHealthCheck(Health.Builder builder) throws Exception {
        boolean checkHealth = check();
        if (checkHealth) {
            builder.up();
        } else {
            builder.down();
        }
        builder.withDetail("code", "2xx/4xx/5xx")
                .withDetail("msg", "i am laker, i am ok!");
    }

    private boolean check() {
        return RandomUtil.randomBoolean();
    }

}