/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.service;

import com.yzf.vo.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/18 20:25
 */
@Service
public class UserService {

    @Cacheable(value="findById", key="#uid",condition = "#uid != null")
    public User findById(Integer uid){
        Random random = new Random();
        if(uid == 1){
            return User.builder().uid(uid).userName("姚明").age(random.nextInt(100)).build();
        }
        return  User.builder().uid(uid).userName("钱多多").age(random.nextInt(20)).build();
    }

    @CacheEvict(value="findById", key="#uid",condition = "#uid != null")
    public int deleteById(int id) {
        return 1;
    }

//    @CacheEvict(value="findById", key="#uid",condition = "#uid != null")
//    public User update(int uid) {
//        Random random = new Random();
//        if(uid == 1){
//            return User.builder().uid(uid).userName("瑶瑶").age(random.nextInt(100)).build();
//        }
//        return  User.builder().uid(uid).userName("钱少少").age(random.nextInt(20)).build();
//    }
}
