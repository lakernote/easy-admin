package com.laker.admin.framework.ext.actuator;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EasyAdminInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> info = new HashMap<>();
        String springBootVersion = SpringBootVersion.getVersion();
        info.put("boot", springBootVersion);
        info.put("email", "xxx@xxx.com");
        builder.withDetail("spring", info);
    }
}