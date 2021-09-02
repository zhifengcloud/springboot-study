/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


@RestController
public class TestController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = true) @NotBlank String msg){

        return "hello:"+msg;
    }
}
