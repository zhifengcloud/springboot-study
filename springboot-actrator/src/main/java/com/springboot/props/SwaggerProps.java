/*
 * @author: wangjian (jonath@163.com)
 * Copyright 2018
 */
package com.springboot.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 配置
 *
 * @author: wangjian (jonath@163.com)
 * @date: 2018/8/15 0:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProps {

    private Docket docket;
    private ApiInfo apiInfo;
    private Contact contact;

    @Data
    public static class Docket {
        private boolean enable;
        private String host;
    }

    @Data
    public static class ApiInfo {
        private String title;
        private String description;
        private String version;
    }

    @Data
    public static class Contact {
        private String name;
        private String url;
        private String email;
    }
}
