/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.service;

import com.yzf.po.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static String personCollectionName = "person";

    @Autowired
    private MongoService mongoService;


    public Person savePerson(Person person) {
        return mongoTemplate.save(person, personCollectionName);
    }

    public List<Person> findByRegion(String region) {
        Query query = Query.query(Criteria.where("region").is("中国"));
        return mongoTemplate.find(query, Person.class);
    }

    /**
     * 分页查询，
     * @param pageNo  页码从0开始
     * @param pageSize
     * @return
     */
    public PageImpl<Person> PageFindAll(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "_id");
//        Criteria criteria = new Criteria();
//        List<Criteria> criteriaList = new ArrayList<>();
//        criteriaList.add(Criteria.where("region").is("中国"));
//        criteria.andOperator(criteriaList.toArray(new Criteria[0]));
//        Query query = Query.query(criteria).with(pageable);
//        List<Person> items = mongoTemplate.find(query, Person.class, personCollectionName);
//        int count = (int) mongoTemplate.count(query, Person.class, personCollectionName);
//        return (PageImpl<Person>) PageableExecutionUtils.getPage(items, pageable, () -> count);

        Criteria criteria = Criteria.where("region").is("中国");
        Sort sort = Sort.by(Sort.Direction.ASC, "_id");
        return (PageImpl<Person>) mongoService.getPage(pageNo, pageSize, criteria, sort, Person.class);
    }
}
