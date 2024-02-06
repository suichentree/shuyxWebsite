package com.shuyx.shuyxuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //swagger访问地址： http://localhost:38010/swagger-ui.html

    //Docket是Swagger规范中的唯一的实体bean，它是生成API文档的核心对象，里面配置一些必要的信息
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描指定包中的注解
                .apis(RequestHandlerSelectors.basePackage("com.shuyx.shuyxuser.controller"))
                .build();
    }
    //设置Swagger文档中的作者信息等
    private ApiInfo apiInfo() {
        Contact contact = new Contact("shuyx", "", "");
        return new ApiInfoBuilder()
                .title("shuyx-user swagger文档")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
