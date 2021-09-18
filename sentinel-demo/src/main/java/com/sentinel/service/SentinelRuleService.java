/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.service;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sentinel.config.SentinelProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/9/1 18:53
 */
//@Service
public class SentinelRuleService {


    @Autowired
    private SentinelProps sentinelProps;

    @Value("${spring.application.name}")
    private String appName;


    public void loadFlowRules() {
        // 流控规则数据源
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(sentinelProps.getRemoteAddress(),
                sentinelProps.getFlowPath() + "/" + appName,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        System.out.println("Zk-----loadFlowRules:");
        System.out.println(flowRuleDataSource.getProperty());
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

    public void loadGradeRules() {
        // 熔断规则数据源
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new ZookeeperDataSource<>(sentinelProps.getRemoteAddress(),
                sentinelProps.getDegradePath() + "/" + appName,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));
        System.out.println("Zk-----loadDegradeRules:");
        System.out.println(degradeRuleDataSource.getProperty());
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
    }
}
