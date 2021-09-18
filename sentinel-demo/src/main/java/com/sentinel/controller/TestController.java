/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/8/27 11:08
 */
@RestController
@RequestMapping("/v1")
public class TestController {

    @GetMapping(value = "/test4")
    public String test4(){
        return"welcome test4";
    }


    // http://localhost:8888/v1/test3
//    @SentinelResource(value = "test3", blockHandler = "exceptionHandler")
    @SentinelResource(value = "test3")
    @GetMapping(value = "/test3")
    public String test3(){
        return"welcome test3";
    }

    public String exceptionHandler(BlockException e) {
        return "服务器限流，请稍后再试";
    }


    @GetMapping(value = "/test")
    public String test(){
        Entry entry = null;
        try {
            entry = SphU.entry("test");
            // 资源中的逻辑.
            System.out.println("hello test");
            return "测试控制test";
        } catch (BlockException e1) {
            System.out.println("blocked!");
            return"服务器繁忙，请稍后再试";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

    }

    @GetMapping(value = "/test2")
    public String test2(){
        Entry entry = null;
        try {
            entry = SphU.entry("HelloWorld");
            // 资源中的逻辑.
            System.out.println("hello world");
            return "测试控制";
        } catch (BlockException e1) {
            System.out.println("blocked!");
            return"服务器繁忙，请稍后再试";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }

    }

    @PostConstruct
    public void initFlowRules(){
        System.out.println("=====initFlowRules========");
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
