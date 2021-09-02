/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.controller;

import com.yzf.po.Person;
import com.yzf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/19 12:20
 */
@RestController
@RequestMapping("/person")
public class PersonController {


    private String[] citys = new String[]{"上海", "深圳", "北京", "广州", "长沙"};

    @Autowired
    private PersonService personService;

    @GetMapping("/save")
    public Person save(String name) {
        Random random = new Random();
        String city = citys[random.nextInt(citys.length)];
        Person person = Person.builder().age(random.nextInt(80)).name(name).city(city).region("中国").build();
        return personService.savePerson(person);
    }

    @GetMapping("/findByRegion")
    public List<Person> findByRegion(String region){
        return personService.findByRegion(region);
    }

    @GetMapping("/findAll")
    public PageImpl<Person> findAll(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        return personService.PageFindAll(pageNo, pageSize);
    }
}
