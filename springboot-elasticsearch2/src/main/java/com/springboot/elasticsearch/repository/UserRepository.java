/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.repository;

import com.springboot.elasticsearch.po.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/21 14:43
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<User, Integer> {

}
