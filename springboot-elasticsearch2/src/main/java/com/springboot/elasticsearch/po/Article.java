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

import java.util.Date;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/21 14:36
 */
@Data
@Document(indexName = "article", type = "article")
//@Document(indexName = "article", shards = 2, replicas = 2)
@Builder
public class Article {

    @Id
    private String id;


    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Keyword)
    private String content;

    @Field(store = true, name = "create_time")
    private Date createTime;

}