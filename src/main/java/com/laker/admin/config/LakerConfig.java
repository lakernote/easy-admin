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
@ConfigurationProperties(prefix = "laker")
public class LakerConfig {
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

    public static class Nginx {
        /**
         * nginx路径
         */
        private String path;
        /**
         * nginx配置文件路径
         */
        private String confPath = "file/nginx.conf";

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getConfPath() {
            return confPath;
        }

        public void setConfPath(String confPath) {
            this.confPath = confPath;
        }
    }

    public static class Waf {
        private boolean xssEnabled = true;
        private boolean sqlEnabled = true;
        private String excludes = "";

        public boolean isXssEnabled() {
            return xssEnabled;
        }

        public void setXssEnabled(boolean xssEnabled) {
            this.xssEnabled = xssEnabled;
        }

        public boolean isSqlEnabled() {
            return sqlEnabled;
        }

        public void setSqlEnabled(boolean sqlEnabled) {
            this.sqlEnabled = sqlEnabled;
        }

        public String getExcludes() {
            return excludes;
        }

        public void setExcludes(String excludes) {
            this.excludes = excludes;
        }
    }

}