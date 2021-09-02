/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sentinel.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/8/27 13:12
 */
@RestController
@RequestMapping("/v1")
public class SphUController {

    @GetMapping(value = "/sentinel_sphu")
    public String sentinel_try_cache(){
        Entry entry = null;
        try {
            entry = SphU.entry("test");
            // 资源中的逻辑.
            System.out.println("hello test");
            return "测试控制-by tyr-catch";
        } catch (BlockException e1) {
            System.out.println("blocked!");
            return"服务器繁忙，请稍后再试";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

    }

    @GetMapping(value = "/sentinel_boolean")
    public boolean sentinel_boolean(){

        if(SphO.entry("sentinel_boolean")){
            try {//被保护的资源
                System.out.println("测试控制-by sentinel_boolean");
                return true;
            } finally {
                //限流出口
                SphO.exit();
            }
        }else {
            System.out.println("服务器繁忙-sentinel_boolean，请稍后再试");
            return false;
        }
    }

    @Autowired
    private TestService testService;

    @GetMapping(value = "/sentinel_async")
    public String sentinel_async(){

        Entry entry = null;
        try {
            entry = SphU.asyncEntry("sentinel_async");
            testService.test();
            // 资源中的逻辑.
            System.out.println("hello sentinel_async");
            return "测试控制-sentinel_async";
        } catch (BlockException e1) {
            System.out.println("blocked-sentinel_async!");
            return"服务器繁忙-sentinel_async，请稍后再试";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
