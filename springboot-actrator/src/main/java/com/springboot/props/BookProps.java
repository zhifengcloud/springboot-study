/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：自定义配置（优雅实现）
 *
 * @author Ay
 * @create 2019/08/31
 **/
@Configuration
@ConfigurationProperties(prefix = "com.book")
@Data
public class BookProps {
    private String name;

    private String author;

    private String randomValue;

    private Integer randomInt;

    private Long randomLong;

    private String randomUuid;

    private Integer randomLen;

    private Integer randomRange;
}
