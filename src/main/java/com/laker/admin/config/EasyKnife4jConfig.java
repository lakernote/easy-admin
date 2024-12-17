package com.laker.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口文档描述，访问地址：http://ip:port/doc.html
 *
 * @author laker
 */
@Configuration
public class EasyKnife4jConfig {
    /**
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            // 获取所有 Paths 并按自定义规则排序
            Paths sortedPaths = sortPathsByCustomRule(openApi.getPaths());
            openApi.setPaths(sortedPaths);
        };
    }

    private Paths sortPathsByCustomRule(Paths originalPaths) {
        // 使用 LinkedHashMap 保持顺序
        Map<String, PathItem> sortedPathItems = originalPaths.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 自定义排序规则
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // 显式指定泛型
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        Paths::new
                ));

        // 创建新的 Paths 对象
        Paths sortedPaths = new Paths();
        sortedPathItems.forEach(sortedPaths::addPathItem);
        return sortedPaths;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyAdminAPI")
                        .contact(new Contact()
                                .name("laker")
                                .url("https://laker.blog.csdn.net")
                                .email("your-email@example.com"))
                        .version("1.0")
                        .description("EasyAdmin接口文档")
                        .termsOfService("https://laker.blog.csdn.net")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi extApi() {
        return GroupedOpenApi.builder()
                .group("0.ext")
                .pathsToMatch("/**")
                .packagesToScan("com.laker.admin.module.ext")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("1.public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("2.admin")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi wxMiniAppApi() {
        return GroupedOpenApi.builder()
                .group("3.wxMiniApp")
                .pathsToMatch("/wx/miniapp/**")
                .build();
    }

}