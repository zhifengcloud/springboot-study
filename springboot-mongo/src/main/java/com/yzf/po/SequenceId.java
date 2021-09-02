/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "sequence")
public class SequenceId {

    @Id
    private String id;

    @Field
    private String collection;

    @Field
    private Long seqId;

}
