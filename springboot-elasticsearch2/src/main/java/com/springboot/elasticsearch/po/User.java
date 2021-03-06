/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.po;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "users", type = "user")
@Builder
public class User {
    @Id
    @Field(type = FieldType.Integer)
    private int id;
    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Keyword)
    private String password;
    private int age;


}
