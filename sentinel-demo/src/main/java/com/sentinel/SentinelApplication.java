/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2020/3/3 9:49
 */
@SpringBootApplication
@EnableAsync
public class SentinelApplication {

    /**
     * 当前登陆用户
     **/
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }
}
