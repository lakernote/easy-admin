package com.laker.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 增加依赖
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-configuration-processor</artifactId>
 * <optional>true</optional>
 * </dependency>
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "admin")
public class AdminConfig {
    /**
     * 名称
     */
    private String name;
    private int port = 9797;
    private List<String> addresses = new ArrayList<>(Arrays.asList("a", "b"));
    private ContainerType containerType = ContainerType.SIMPLE;

    public enum ContainerType {
        SIMPLE,
        DIRECT

    }

    private Host host;

    public static class Host {
        private String ip;
        private int port;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

}