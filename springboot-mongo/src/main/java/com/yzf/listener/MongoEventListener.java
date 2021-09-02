/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.listener;

import com.yzf.autoid.AutoIncrement;
import com.yzf.po.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author yuanzf
 * @version 1.0
 * @Description 重写 mongo 的监听器，在保存数据时获取自增序号
 * @date 2021/5/19 10:45
 * @className MongoEventListener
 */
@Component
public class MongoEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                // 如果字段添加了我们自定义的 AutoIncrement 注解
                if (field.isAnnotationPresent(AutoIncrement.class)) {
                    // 设置自增ID
                    field.set(source, getNextId(source.getClass().getSimpleName()));
                }
            });
        }
    }


    private Long getNextId(String collectionName) {
        Query query = new Query(Criteria.where("collection").is(collectionName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequenceId seq = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
        return seq.getSeqId();
    }

}
