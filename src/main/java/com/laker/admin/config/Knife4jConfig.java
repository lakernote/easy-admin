package com.laker.admin.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 接口文档描述，访问地址：http://ip:port/doc.html
 *
 * @author laker
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("EasyAdmin 1.0 接口文档")
                        .description("EasyAdmin RESTful APIs")
                        .termsOfServiceUrl("https://gitee.com/lakernote/easy-admin")
                        .contact(new Contact("laker", "https://laker.blog.csdn.net", "935009066@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("EasyAdmin 1.X版本")
                .select()
                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.github.xiaoymin.knife4j.controller"))
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
}
