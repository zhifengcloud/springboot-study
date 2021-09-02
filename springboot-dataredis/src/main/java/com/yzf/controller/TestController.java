/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.controller;

import com.yzf.configure.utils.RedisUtils;
import com.yzf.service.UserService;
import com.yzf.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;


@RestController
public class TestController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/getUuid")
    public Object getUuid(HttpServletRequest request){
        String key = "uuid";
        Object uuid = redisUtils.get(key);
        if(null == uuid){
            uuid = UUID.randomUUID().toString();
            redisUtils.set(key,uuid,60);
        }
        return  new Random().nextInt(1000) +" ："+ uuid;
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable(value = "id")  Integer id){
        System.out.println("id:"+id);
        System.out.println("userService:"+userService);
        return userService.findById(id);
    }


}
