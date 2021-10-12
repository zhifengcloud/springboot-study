/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.configure;

import com.springboot.props.SwaggerProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProps swaggerProps;

    // http://localhost:8090/swagger-ui.html

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                .enable(swaggerProps.getDocket().isEnable())
                .host(swaggerProps.getDocket().getHost())
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.springboot.controller"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(swaggerProps.getContact().getName(), swaggerProps.getContact().getUrl(), swaggerProps.getContact().getEmail());
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title(swaggerProps.getApiInfo().getTitle())
                //文档描述
                .description(swaggerProps.getApiInfo().getDescription())
                //版本号
                .version(swaggerProps.getApiInfo().getVersion())
                //服务条款URL
                .termsOfServiceUrl("http://localhost:8090/")
                // 联系方式
                .contact(contact)
                .build();
    }

}