package com.saliai.wechat_3rdparty.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author:chenyiwu
 * @Describtion: Swagger接入
 * @Create Time:2018/6/14
 */
@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.saliai.wechat_3rdparty.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微信第三方平台构建api文档")
                .description("简单优雅的restfun风格，微信第三方平台构建api文档")
                .termsOfServiceUrl("https://baidu.com")
                .version("1.0")
                .build();
    }
}
