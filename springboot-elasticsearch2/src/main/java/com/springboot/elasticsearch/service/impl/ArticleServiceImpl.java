/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.service.impl;

import com.springboot.elasticsearch.po.Article;
import com.springboot.elasticsearch.repository.ArticleRepository;
import com.springboot.elasticsearch.service.ArticleService;
import io.micrometer.core.instrument.util.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/21 16:25
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Autowired
    private RestHighLevelClient restHighLevelClient;




    @Override
    public Article findById(String id) {

        GetQuery query = new GetQuery(id);
        Article article = elasticsearchRestTemplate.queryForObject(query, Article.class);
        System.out.println("Article:"+article);

        Optional<Article> optional = articleRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public void deleteById(String id) {
        elasticsearchRestTemplate.delete(id, Article.class);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void saveAll(List<Article> list) {
        articleRepository.saveAll(list);
    }

    @Override
    public Iterator<Article> findAll() {
        return articleRepository.findAll().iterator();
    }



    @Override
    public boolean indexExists(String indexName) {
//        GetIndexRequest request = new GetIndexRequest(indexName);
//        boolean exists = restHighLevelClient.indices()..exists(request, RequestOptions.DEFAULT);
//        return exists;
        return false;
    }

    @Override
    public void  pageByTitle(String content, int pageNo, int pageSize) throws IOException {
        /**
         * 特别注意：es的分页从0页开始
         * ES 查询默认最多返回10000条数据，超过10000条需要修改 ES 设置或使用滚动查询。
         * ES 分页方式会用新的章节讲解
         */

//        BoolQueryBuilder bool = new BoolQueryBuilder();
//        bool.must(QueryBuilders.matchQuery("title", title));
//        minimumShouldMatch("100%")
//        bool.must(QueryBuilders.termQuery("level", dto.getLevel().toLowerCase()));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        // 设置查询条件BoolQueryBuilder
//        sourceBuilder.query(bool);

        //多值匹配 即：输入的关键字将会在title和keywords两者中间匹配，类似mysql or
        MultiMatchQueryBuilder termQueryBuilder = QueryBuilders.multiMatchQuery(content,"title","keywords");
        sourceBuilder.query(termQueryBuilder);

        // 设置分组，需注意 es 的分页是从 0 开始的
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        // 设置排序
        sourceBuilder.sort("id", SortOrder.DESC);

        // 此处的index名称是在实体bean配置的那个index,表明你要检索哪个索引
        SearchRequest searchRequest = new SearchRequest("article");
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        // 获取结果集
        SearchHits hits = response.getHits();
//        Page<Article> page = new PageImpl<>();
        // 获取总条数
        int total = Integer.valueOf(String.valueOf(hits.getTotalHits().value));
        System.out.println("total:"+total);
        // 转换结果集
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }




//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                //查询条件
//                .withQuery(QueryBuilders.queryStringQuery("浦东开发开放").defaultField("title"))
//                //分页
//                .withPageable(PageRequest.of(0, 5))
//                //排序
//                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//                //高亮字段显示
//                .withHighlightFields(new HighlightBuilder.Field("浦东"))
//                .build();
//        List<Article> articleEntities = elasticsearchRestTemplate.queryForList(nativeSearchQuery, Article.class);
//        articleEntities.forEach(item -> System.out.println(item.toString()));

    }

    public void searchScroll(String scrollId, Integer size){
        // 滚动查询 scroll api
        if (size == null || size <= 0) {
//            "请输入每页查询数"
          return;
        }
        NativeSearchQuery query = new NativeSearchQuery(new BoolQueryBuilder());
        query.setPageable(PageRequest.of(0, size));
        SearchScrollHits<Article> searchHits = null;
        if (StringUtils.isEmpty(scrollId)) {
            // 开启一个滚动查询，设置该 scroll 上下文存在 60s
            // 同一个 scroll 上下文，只需要设置一次 query（查询条件）
            searchHits = elasticsearchRestTemplate.searchScrollStart(60000, query, Article.class, IndexCoordinates.of("article"));
            if (searchHits instanceof SearchHitsImpl) {
                scrollId = ((SearchHitsImpl) searchHits).getScrollId();
            }
        } else {
            // 继续滚动
            searchHits = elasticsearchRestTemplate.searchScrollContinue(scrollId, 60000, Article.class, IndexCoordinates.of("article"));
        }

//        List<Article> articles = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
//        if (articles.size() == 0) {
//            // 结束滚动
//            elasticsearchRestTemplate.searchScrollClear(Collections.singletonList(scrollId));
//            scrollId = null;
//        }
//
//        if (scrollId == null) {
//            return new JsonResult(false, "已到末尾");
//        } else {
//            JsonResult jsonResult = new JsonResult(true);
//            jsonResult.put("count", searchHits.getTotalHits());
//            jsonResult.put("size", articles.size());
//            jsonResult.put("articles", articles);
//            jsonResult.put("scrollId", scrollId);
//            return jsonResult;
//        }

    }
}
