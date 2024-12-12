package com.laker.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义配置
 *
 * @author laker
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "easy")
public class EasyAdminConfig {
    /**
     * log配置
     */
    private String logFilePath = "logs/laker.log";

    /**
     * 用户初始密码
     */
    private String defaultPwd = "lakernote";

    /**
     * 防火墙
     */
    private Waf waf = new Waf();

    /**
     * nginx
     */
    private Nginx nginx = new Nginx();

    /**
     * 文件存储
     */
    private Storage storage = new Storage();

    private Trace trace = new Trace();

    @Data
    public static class Nginx {
        /**
         * nginx路径
         */
        private String path;
        /**
         * nginx配置文件路径
         */
        private String confPath = "file/nginx.conf";
    }

    @Data
    public static class Waf {
        private boolean xssEnabled = true;
        private boolean sqlEnabled = true;
        private String excludes = "";
    }

    @Data
    public static class Storage {
        private Local local = new Local();
        private Aliyun aliyun = new Aliyun();
    }

    @Data
    public static class Local {
        private boolean enable = true;
        private String address = "http://localhost:8080";
        private String storagePath = "storage";

    }

    @Data
    public static class Aliyun {
        private boolean enable;
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }

    @Data
    public static class Trace {
        /**
         * trace最小耗时
         */
        private long time = 1000L;

        private long httpTime = 1000L;

        private long transactionTime = 1000L;
    }
}