/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.configure;

import com.springboot.props.SwaggerProps;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


public class SwaggerConfigOld {

    private SwaggerProps swaggerProps;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                //.enable(false)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.springboot.controller"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("SpringBoot中使用Swagger2接口规范")
                //文档描述
                .description("接口说明")
                //服务条款URL
                .termsOfServiceUrl("http://localhost:8090/")
                //版本号
                .version("1.0.0")
                .build();
    }

//    SpringSecurity中配置
//    如果Spring Boot项目中集成了Spring Security，接口会被拦截，需要在Spring Security的配置类中重写configure方法，对接口进行过滤一下。代码如下：
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/swagger-ui.html")
//                .antMatchers("/v2/**")
//                .antMatchers("/swagger-resources/**");
//    }
//
//    原文链接：https://blog.csdn.net/qq_40205116/article/details/105200104

}