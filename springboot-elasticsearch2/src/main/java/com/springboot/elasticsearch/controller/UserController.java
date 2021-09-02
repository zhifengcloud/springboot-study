/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.controller;

import com.springboot.elasticsearch.po.User;
import com.springboot.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userDao;

    @PostMapping("/addUser")
    public String addUser(String username, String password, Integer age) {
        User user = User.builder().username(username)
                .password(password).age(age).build();
        return String.valueOf(userDao.save(user).getId());// 返回id做验证
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(Integer id) {
        userDao.deleteById(id);
        return "Success!";
    }

    @PutMapping("/updateUser")
    public String updateUser(Integer id, String username, String password, Integer age) {
        User user = User.builder().username(username).id(id)
                .password(password).age(age).build();
        return String.valueOf(userDao.save(user).getId());// 返回id做验证
    }

    @GetMapping("/getUser")
    public User getUser(Integer id) {
        return userDao.findById(id).get();
    }

    @GetMapping("/getAllUsers")
    public Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

//    @GetMapping("/pageQuery")
//    public Page<User> pageQuery(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, String userName) {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchPhraseQuery("username", userName))
//                .withPageable(PageRequest.of(pageNo, pageSize))
//                .build();
//        return userDao.search(searchQuery);
//    }
}
