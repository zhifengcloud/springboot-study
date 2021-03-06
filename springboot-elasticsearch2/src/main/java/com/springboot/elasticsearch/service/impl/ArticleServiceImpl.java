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
         * ???????????????es????????????0?????????
         * ES ????????????????????????10000??????????????????10000??????????????? ES ??????????????????????????????
         * ES ????????????????????????????????????
         */

//        BoolQueryBuilder bool = new BoolQueryBuilder();
//        bool.must(QueryBuilders.matchQuery("title", title));
//        minimumShouldMatch("100%")
//        bool.must(QueryBuilders.termQuery("level", dto.getLevel().toLowerCase()));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        // ??????????????????BoolQueryBuilder
//        sourceBuilder.query(bool);

        //???????????? ?????????????????????????????????title???keywords???????????????????????????mysql or
        MultiMatchQueryBuilder termQueryBuilder = QueryBuilders.multiMatchQuery(content,"title","keywords");
        sourceBuilder.query(termQueryBuilder);

        // ???????????????????????? es ??????????????? 0 ?????????
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        // ????????????
        sourceBuilder.sort("id", SortOrder.DESC);

        // ?????????index??????????????????bean???????????????index,??????????????????????????????
        SearchRequest searchRequest = new SearchRequest("article");
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        // ???????????????
        SearchHits hits = response.getHits();
//        Page<Article> page = new PageImpl<>();
        // ???????????????
        int total = Integer.valueOf(String.valueOf(hits.getTotalHits().value));
        System.out.println("total:"+total);
        // ???????????????
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }




//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                //????????????
//                .withQuery(QueryBuilders.queryStringQuery("??????????????????").defaultField("title"))
//                //??????
//                .withPageable(PageRequest.of(0, 5))
//                //??????
//                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
//                //??????????????????
//                .withHighlightFields(new HighlightBuilder.Field("??????"))
//                .build();
//        List<Article> articleEntities = elasticsearchRestTemplate.queryForList(nativeSearchQuery, Article.class);
//        articleEntities.forEach(item -> System.out.println(item.toString()));

    }

    public void searchScroll(String scrollId, Integer size){
        // ???????????? scroll api
        if (size == null || size <= 0) {
//            "????????????????????????"
          return;
        }
        NativeSearchQuery query = new NativeSearchQuery(new BoolQueryBuilder());
        query.setPageable(PageRequest.of(0, size));
        SearchScrollHits<Article> searchHits = null;
        if (StringUtils.isEmpty(scrollId)) {
            // ???????????????????????????????????? scroll ??????????????? 60s
            // ????????? scroll ????????????????????????????????? query??????????????????
            searchHits = elasticsearchRestTemplate.searchScrollStart(60000, query, Article.class, IndexCoordinates.of("article"));
            if (searchHits instanceof SearchHitsImpl) {
                scrollId = ((SearchHitsImpl) searchHits).getScrollId();
            }
        } else {
            // ????????????
            searchHits = elasticsearchRestTemplate.searchScrollContinue(scrollId, 60000, Article.class, IndexCoordinates.of("article"));
        }

//        List<Article> articles = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
//        if (articles.size() == 0) {
//            // ????????????
//            elasticsearchRestTemplate.searchScrollClear(Collections.singletonList(scrollId));
//            scrollId = null;
//        }
//
//        if (scrollId == null) {
//            return new JsonResult(false, "????????????");
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
