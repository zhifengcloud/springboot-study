/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.service;

import org.springframework.stereotype.Component;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/8/27 13:18
 */
@Component
public class TestService {

    public void test(){
        System.out.println("开始执行任务");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束执行任务");
    }
}
