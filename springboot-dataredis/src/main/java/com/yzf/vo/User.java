/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/18 20:25
 */
@Data
@ToString
@Builder
public class User {

    private Integer uid;

    private String userName;

    private Integer age;
}
