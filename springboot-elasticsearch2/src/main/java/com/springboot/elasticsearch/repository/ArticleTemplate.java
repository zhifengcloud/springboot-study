/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.elasticsearch.repository;

//@Repository
public class ArticleTemplate {

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;
//
//    /**
//     * 简单的模糊查询
//     * @param title
//     * @return
//     */
//    public List<Article> queryByTitle(String title){
//        return elasticsearchTemplate.queryForList(
//                new CriteriaQuery(Criteria.where("title").contains(title)), Article.class);
//    }
//
//    /**
//     * 根据标题全文检索，高亮显示分词结果
//     * @param keyword
//     * @return
//     */
//    public List<Article> query(String keyword) {
//        NativeSearchQueryBuilder query = buildQuery(keyword);
//        return buildResult(query);
//    }
//
//    /**
//     * 根据标题全文检索，高亮显示分词结果,分页查询
//     * @param keyword
//     * @return
//     */
//    public List<Article> queryByPage(String keyword, int page, int limit) {
//        NativeSearchQueryBuilder query = buildQuery(keyword);
//        query.withPageable(PageRequest.of(page, limit));
//        return buildResult(query);
//    }
//
//    private List<Article> buildResult(NativeSearchQueryBuilder query) {
//        return elasticsearchTemplate.query(query.build(), new ResultsExtractor<List<Article>>() {
//
//            @Override
//            public List<Article> extract(SearchResponse response) {
//                List<Article> list = new ArrayList<Article>();
//                for (SearchHit hit : response.getHits()) {
//                    Article r = new Article();
//                    r.setId(Integer.parseInt(hit.getId()));
//                    r.setTitle(hit.getHighlightFields().get("title").fragments()[0].toString());
////                    r.setUrl(hit.getSource().get("url").toString());
////                    r.setContent(hit.getSource().get("content").toString());
//                    list.add(r);
//                }
//                return list;
//            }
//
//        });
//    }
//
//    private NativeSearchQueryBuilder buildQuery(String keyword) {
//        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
//        query.withIndices("cxytiandi");
//        query.withTypes("article");
//        query.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
//        query.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title",keyword)));
//        return query;
//    }
//
//    /**
//     * 标题检索结果总数量
//     * @param keyword
//     * @return
//     */
//    public Long queryTitleCount(String keyword) {
//        NativeSearchQueryBuilder query = buildQuery(keyword);
//        return elasticsearchTemplate.count(query.build());
//    }
//
//    /**
//     * 查询sid下的标题信息，相当于sql中的 select * from article where title like '%keyword%' and sid = sid
//     * @param keyword
//     * @param sid
//     * @return
//     */
//    public List<Article> query(String keyword, String sid) {
//        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
//        query.withIndices("cxytiandi");
//        query.withTypes("article");
//        query.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
//        query.withQuery(
//                QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("title",keyword))
//                        .must(QueryBuilders.matchQuery("sid", sid))
//        );
//        return buildResult(query);
//    }
//
//    /**
//     * 查询sid下的标题信息，相当于sql中的 select * from article where title like '%keyword%' or sid = sid
//     * @param keyword
//     * @param sid
//     * @return
//     */
//    public List<Article> queryByOr(String keyword, String sid) {
//        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
//        query.withIndices("cxytiandi");
//        query.withTypes("article");
//        query.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
//        query.withQuery(
//                QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("title",keyword))
//                        .should(QueryBuilders.matchQuery("sid", sid))
//        );
//        return buildResult(query);
//    }

}