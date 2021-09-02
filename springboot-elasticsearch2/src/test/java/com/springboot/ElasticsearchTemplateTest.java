/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot;

import com.springboot.elasticsearch.Application;
import com.springboot.elasticsearch.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticsearchTemplateTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void testInsert() {
        User user = User.builder().username("姚明").age(36).password("99855").id(12).build();

        user = elasticsearchRestTemplate.save(user);
        System.out.println(user);
    }

    @Test
    public void testQuery() {

    }
}
