/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.controller;

import com.springboot.pojo.User;
import com.springboot.props.BookProps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "测试访问接口", tags = "user")
public class TestController {

    @Autowired
    private BookProps bookProps;

    @GetMapping("/hello")
    @ApiOperation(value = "打招呼接口", notes = "测试打招呼接口")
    public String sayHello(@ApiParam(value = "招呼语", required = false) @RequestParam(required = false, defaultValue = "小龙") String msg) {
        return "hello:" + msg;
    }

    @PostMapping("/user")
    @ApiOperation(value ="查询用户", notes = "查询用户")//给接口添加注释
    //@ApiImplicitParam(name = "id",value = "用户编号",required = true) 假设有参数可以设置 参数，参数名称，是否必传参数
    public User user( @ModelAttribute User user) {
        return user;
    }

    @GetMapping("/book")
    @ApiOperation(value ="查询书", notes = "查询书的详细信息")
    public BookProps book() {
        return bookProps;
    }
}
