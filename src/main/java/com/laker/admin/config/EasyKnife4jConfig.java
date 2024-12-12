package com.laker.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            // 设置全局的信息
//            if (openApi.getPaths() != null) {
//                openApi.addExtension("x-test123", "333");
//                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
//            }
        };
    }


    @Bean
    public OpenAPI customOpenAPI() {
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