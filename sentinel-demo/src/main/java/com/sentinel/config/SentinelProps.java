/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/9/17 11:17
 */
@Data
@Configuration
@PropertySources({@PropertySource("classpath:sentinel.properties")})
@ConfigurationProperties(prefix = "sentinel.zookeeper")
public class SentinelProps {

    private String remoteAddress;

    private String flowPath;

    private String degradePath;
}
