/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot;

import com.springboot.elasticsearch.Application;
import com.springboot.elasticsearch.po.Article;
import com.springboot.elasticsearch.repository.ArticleRepository;
import com.springboot.elasticsearch.service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/21 15:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ArticleTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Test
    public void saveTest() {
        Article article =  Article.builder().title("郑恺苗苗3庆祝结婚一周年")
                .url("https://s.weibo.com/weibo")
                .content("@郑恺 @苗苗2-vivi 晒出对方吃牛肉面的照片，庆祝结婚一周年。 \u200B")
                .createTime(new Date())
                .build();
        String id = articleRepository.save(article).getId();
        Assert.assertNotNull(id);
        System.out.println(id);
    }

    @Test
    public void addTest() {
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        List<Article> articleList = new ArrayList<>();

        Article article1 =  Article.builder().title("TOP100 全球软件案例研究峰会")
                .url("https://www.weibo.com/weibo")
                .content("HLXXZ-H5-幻灵修仙传").createTime(new Date())
                .build();

        Article article2 =  Article.builder().title("ElasticSearch 集成 Spring 之 ElasticsearchRestTemplate 示例")
                .url("https://www.sina.com/weibo")
                .content("QYJ2-专服-魔境仙迹")
                .createTime(new Date())
                .build();

        articleList.add(article1);
        articleList.add(article2);

        for(Article test:articleList) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(test.getId()).withObject(test).build();
            queries.add(indexQuery);
        }
        List<String> stringList = elasticsearchTemplate.bulkIndex(queries, IndexCoordinates.of("article"));
        Assert.assertNotNull(stringList);
        System.out.println(stringList);
    }

    @Test
    public void findByIdTest(){
        String id="fRyPjnkB9ORor3lBlP3M";
        Article article = articleService.findById(id);
        Assert.assertNotNull(article);
        System.out.println(article);
    }

    @Test
    public void queryListTest() {
        Iterable<Article> list = articleRepository.findAll();
        for (Article article : list) {
//            System.out.println(article.getTitle());
            System.out.println(article);
        }

    }

    @Test
    public void testQuery() {
        Iterable<Article> list = articleRepository.findByTitleContaining("java");
        for (Article article : list) {
            System.out.println(article.getTitle());
        }
    }

    @Test
    public void testQueryByPage() throws Exception{
        int pageNo = 0;
        int pageSize = 2;
        articleService.pageByTitle("苗苗",0,2);

    }
//
//    @Test
//    public void testQueryByTitle() {
//        List<Article> list = articleTemplate.query("java");
//        for (Article article : list) {
//            System.out.println(article.getTitle());
//        }
//    }
//
//    @Test
//    public void testQueryTitleCount() {
//        System.out.println(articleTemplate.queryTitleCount("java"));
//    }
//
//    @Test
//    public void testQueryBySid() {
//        List<Article> list = articleTemplate.query("java", "dak219dksd");
//        for (Article article : list) {
//            System.out.println(article.getTitle());
//        }
//    }
//
//    @Test
//    public void testQueryByOr() {
//        List<Article> list = articleTemplate.queryByOr("java", "dad");
//        for (Article article : list) {
//            System.out.println(article.getTitle());
//        }
//    }
}