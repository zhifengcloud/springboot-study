/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/10/12 17:16
 */
@ApiModel("用户实体类")
@Data
public class User {
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;

}
