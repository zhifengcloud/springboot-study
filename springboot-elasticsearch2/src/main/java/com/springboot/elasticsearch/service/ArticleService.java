/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.service;

import com.springboot.elasticsearch.po.Article;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/21 16:24
 */
public interface ArticleService {

    Article findById(String id);

    void deleteById(String id);

    void save(Article docBean);

    void saveAll(List<Article> list);

    Iterator<Article> findAll();

    public void pageByTitle(String title, int pageNo, int pageSize) throws IOException;

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     */
    boolean indexExists(String indexName);
}
