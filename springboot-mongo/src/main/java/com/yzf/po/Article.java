/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.po;

import com.yzf.autoid.AutoIncrement;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 *
 * @Description tddo
 * @author yuanzf
 * @version 1.0
 * @date 2021/5/19 10:38
 * @className Article
 */
@Data
@Document(collection = "article_info")
public class Article {
    @Id
    @AutoIncrement
    private Long id;

    @Field("title")
    private String title;

    @Field("url")
    private String url;

    @Field("author")
    private String author;

    @Field("tags")
    private List<String> tags;

    @Field("visit_count")
    private Long visitCount;

    @Field("add_time")
    private Date addTime;

}

