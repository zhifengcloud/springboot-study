/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.service;

import org.apache.shardingsphere.elasticjob.lite.example.entity.Foo;
import org.apache.shardingsphere.elasticjob.lite.example.repository.FooRepository;
import org.junit.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/8/3 14:14
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = FooServiceTest.class)
public class FooServiceTest {

    @Resource
    private FooRepository fooRepository;

//    @Test
    public void findByIdTest(){
        fooRepository.init();
        List<Foo> list=  fooRepository.findTodoData("Beijing",10);
        Assert.assertTrue(list.size()>0);
    }
}
