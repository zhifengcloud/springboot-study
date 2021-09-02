/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * mongo 的分页公用代码
 * author: wangjian (jonath@163.com)
 * date: 2020/4/14 14:52
 */
@Service
public class MongoService<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<T> getPage(int pageNo, int pageSize, Criteria criteria, Sort sort, Class clazz) {
        return this.getPage(pageNo, pageSize, criteria, sort, clazz, null);
    }

    public Page<T> getPage(int pageNo, int pageSize, Criteria criteria, Sort sort, Class clazz, String collection) {
        Pageable pageable =  PageRequest.of(pageNo, pageSize, sort);
        Query query = Query.query(criteria).with(pageable);
        List items;
        long count;
        if (StringUtils.isBlank(collection)) {
            items = mongoTemplate.find(query, clazz);
            count = mongoTemplate.count(query, clazz);
        } else {
            items = mongoTemplate.find(query, clazz, collection);
            count = mongoTemplate.count(query, clazz, collection);
        }
        return PageableExecutionUtils.getPage(items, pageable, () -> count);
    }

    public Page<T> getPageById(long seqId, int pageSize, Criteria criteria, Sort sort, Class clazz) {
        return this.getPageById(seqId, null, pageSize, criteria, sort, clazz, null);
    }

    public Page<T> getPageById(long seqId, int pageSize, Criteria criteria, Sort sort, Class clazz, String collection) {
        return this.getPageById(seqId, null, pageSize, criteria, sort, clazz, collection);
    }

    public Page<T> getPageById(long seqId, String field, int pageSize, Criteria criteria, Sort sort, Class clazz) {
        return this.getPageById(seqId, field, pageSize, criteria, sort, clazz, null);
    }

    /**
     * 使用序号快速分页
     * date: 2020/4/15 11:21
     * author: wangjian (jonath@163.com)
     *
     * @param seqId      序号值
     * @param field      序号列名称，默认为 id，指定建议用 seqId
     * @param pageSize   分页大小
     * @param criteria   查询条件
     * @param sort       排序
     * @param clazz      类
     * @param collection 集合
     * @return
     */
    public Page<T> getPageById(long seqId, String field, int pageSize, Criteria criteria, Sort sort, Class clazz, String collection) {
        String seqField = StringUtils.isBlank(field) ? "id" : field;
        return this.getPage(0, pageSize, criteria.and(seqField).gt(seqId), sort, clazz, collection);
    }
}
