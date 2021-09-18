/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.config;

import com.sentinel.service.SentinelRuleService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/8/27 20:58
 * https://blog.csdn.net/xshsjl/article/details/112901768
 */
//@Component
public class SentinelRuleZkConfig {
//        implements CommandLineRunner {


    @Autowired
    private SentinelProps sentinelProps;


    @Value("${spring.application.name}")
    private String appName;

    private static CuratorFramework zkClient;

    @Autowired
    private SentinelRuleService sentinelRuleService;

    private NodeCache nodeCache;

    private NodeCache degradeNodeCache;

    public void init() throws Exception {
        zkClient=  CuratorFrameworkFactory.builder().
                connectString(sentinelProps.getRemoteAddress())//zkClint连接地址
//                        .connectionTimeoutMs(2000)//连接超时时间
//                        .sessionTimeoutMs(10000)//会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(2000, 3))
                //重试策略
//                        .namespace("myZookeeperTest")
                //命名空间,默认节点
                .build();

        zkClient.start();
        String flowRulePath = sentinelProps.getFlowPath() + "/" + appName;
        nodeCache = new NodeCache(zkClient, flowRulePath, false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() == null) {
                    System.out.println("\r\n==流控节点被删除。。。" + nodeCache.getPath() + "数据为：" + new String(nodeCache.getCurrentData().getData()));
                } else {
                    System.out.println("\r\n==流控节点被修改。。。" + nodeCache.getPath() + "数据为：" + new String(nodeCache.getCurrentData().getData()));
//                    System.out.println("\r\n==流控节点被修改。。。" + nodeCache.getPath() + "数据为：" +nodeCache.getCurrentData());
                }
                sentinelRuleService.loadFlowRules();
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();


        String  degradeRulePath = sentinelProps.getDegradePath() + "/" + appName;
        degradeNodeCache = new NodeCache(zkClient, degradeRulePath, false);
        NodeCacheListener degradeNodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() == null) {
                    System.out.println("\r\n==熔断节点被删除。。。" + nodeCache.getPath() + "数据为：" + new String(nodeCache.getCurrentData().getData()));
                } else {
                    System.out.println("\r\n==熔断节点被修改。。。" + nodeCache.getPath() + "数据为：" + new String(nodeCache.getCurrentData().getData()));
                }
                sentinelRuleService.loadGradeRules();
            }
        };
        degradeNodeCache.getListenable().addListener(degradeNodeCacheListener);
        degradeNodeCache.start();
    }


//    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------init--------------");
        this.init();
    }


    @PostConstruct
    public void loadRules() {
        sentinelRuleService.loadFlowRules();
        sentinelRuleService.loadGradeRules();
    }


    @PreDestroy
    public void destroy() throws Exception{
        System.out.println("==========关闭zookeeper连接=========\r\n");
        if (this.nodeCache != null) {
//            this.nodeCache.getListenable().removeListener(listener);
            this.nodeCache.close();
        }
        if (this.degradeNodeCache != null) {
            this.degradeNodeCache.close();
        }
        CloseableUtils.closeQuietly(zkClient);

    }
}
