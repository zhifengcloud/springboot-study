/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.repository;

import com.springboot.elasticsearch.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 接口关系：
 * ElasticsearchRepository --> ElasticsearchCrudRepository --> PagingAndSortingRepository --> CrudRepository
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {

    @Override
    Optional<Article> findById(String id);

    List<Article> findByTitleContaining(String title);

    Page<Article> findByTitle(String title, Pageable pageable);


}