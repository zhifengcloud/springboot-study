/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.utils;


import com.yzf.po.DeviceInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2019/12/11 16:44
 */
public class MongoUtils {

    private static  String EMPTY_IDFA = "00000000-0000-0000-0000-000000000000";
    /**
     * 设备号查询时去掉归因天数
     *
     * @param deviceInfo
     * @return
     */
    public static Query buildRegisterDeviceIdQuery(DeviceInfoVo deviceInfo) {
        Criteria criteria = new Criteria();
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(deviceInfo.getAppID()).and("timestamp").lte(deviceInfo.getTimestamp()));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return Query.query(criteria);
    }

    /**
     * 设备号查询条件
     *
     * @param deviceInfo
     * @param days
     * @return
     */
    public static Query buildDeviceIdQuery(DeviceInfoVo deviceInfo, int days) {
        Criteria criteria = new Criteria();
        Date targetDate = new LocalDate(deviceInfo.getTimestamp() * 1000L).plusDays(0 - days).toDate();
        long ts = targetDate.getTime() / 1000;
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(deviceInfo.getAppID()).and("timestamp").gt(ts).lte(deviceInfo.getTimestamp()));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return Query.query(criteria);
    }

    /**
     * 渠道登陆失败的设备号以及错误码查询条件
     *
     * @param deviceInfo
     * @param days
     * @return
     */
    public static Query buildUnionLoginFailQuery(DeviceInfoVo deviceInfo, String type, int days) {
        Criteria criteria = new Criteria();
        Date targetDate = new LocalDate(deviceInfo.getTimestamp() * 1000L).plusDays(0 - days).toDate();
        long ts = targetDate.getTime() / 1000;
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(deviceInfo.getAppID())
                .and("type").is(type)
                .and("timestamp").gt(ts).lte(deviceInfo.getTimestamp()));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return Query.query(criteria);
    }


    /**
     * 按小时查询
     *
     * @param deviceInfo
     * @param hour
     * @return
     */
    public static Query buildDeviceIdQueryByHour(DeviceInfoVo deviceInfo, int hour) {
        Criteria criteria = new Criteria();
        Date targetDate = new DateTime(deviceInfo.getTimestamp() * 1000L).plusHours(0 - hour).toDate();
        long ts = targetDate.getTime() / 1000;
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(deviceInfo.getAppID()).and("timestamp").gt(ts).lte(deviceInfo.getTimestamp()));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return Query.query(criteria);
    }

    /**
     * 根据设备号全表查询
     *
     * @param deviceInfo
     * @return
     */
    public static Query buildDeviceIdQuery(DeviceInfoVo deviceInfo) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("appID").is(deviceInfo.getAppID()));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return Query.query(criteria);
    }

    /**
     * 根据应用和uid全表查询
     *
     * @param appId
     * @param uid
     * @return
     */
    public static Query buildUidQuery(String appId, String uid) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("appID").is(appId).and("uid").is(uid));
        return Query.query(criteria);
    }



    /**
     * 组装设备号查询条件
     *
     * @param deviceInfo
     * @return
     */
    public static List<Criteria> buildCriteriaList(DeviceInfoVo deviceInfo) {
        List<Criteria> criteriaList = new ArrayList<>();
        if (StringUtils.isNotBlank(deviceInfo.getImei1())) {
            criteriaList.add(Criteria.where("imei1").is(deviceInfo.getImei1()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getImei2())) {
            criteriaList.add(Criteria.where("imei2").is(deviceInfo.getImei2()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getAndroidId())) {
            criteriaList.add(Criteria.where("androidId").is(deviceInfo.getAndroidId()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getOaid()) && !EMPTY_IDFA.equals(deviceInfo.getOaid())) {
            criteriaList.add(Criteria.where("oaid").is(deviceInfo.getOaid()));
        }
        //idfa不为空并且不为00000000-0000-0000-0000-000000000000，否则不增加查询条件
        if (StringUtils.isNotBlank(deviceInfo.getIdfa()) && !EMPTY_IDFA.equals(deviceInfo.getIdfa())) {
            criteriaList.add(Criteria.where("idfa").is(deviceInfo.getIdfa()));
        }
        if (StringUtils.isNotBlank(deviceInfo.getIdfv())) {
            criteriaList.add(Criteria.where("idfv").is(deviceInfo.getIdfv()));
        }
        return criteriaList;
    }


    /**
     * 用户查询条件
     *
     * @param appId
     * @param uid
     * @param ts
     * @param timeStamp
     * @return
     */
    private static Criteria queryCriteria(String appId, String uid, Long ts, int timeStamp) {
        Criteria criteria = new Criteria();
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(appId)
                .and("uid").is(uid)
                .and("timestamp").gt(ts).lte(timeStamp));
        return criteria;
    }

    /**
     * 用户查询条件
     *
     * @param appId
     * @param uid
     * @param timestamp
     * @param ts
     * @param deviceInfo
     * @return
     */
    private static Criteria queryCriteria(String appId, String uid, int timestamp, long ts, DeviceInfoVo deviceInfo) {
        Criteria criteria = new Criteria();
        // lt 小于；gt 大于
        criteria.andOperator(Criteria.where("appID").is(appId)
                .and("uid").is(uid)
                .and("timestamp").gt(ts).lt(timestamp));
        List<Criteria> criteriaList = buildCriteriaList(deviceInfo);
        // 组装设备号查询条件
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }
        return criteria;
    }

    /**
     * 根据起始、结束时间戳和appid组装查询语句
     *
     * @param begin
     * @param until
     * @param appid
     * @return
     */
    public static Criteria queryCriteria(long begin, long until, String appid) {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("timestamp").gte(begin).lt(until));
        if (StringUtils.isNotBlank(appid)) {
            criteriaList.add(Criteria.where("appID").is(appid));
        }
        criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        return criteria;
    }

    /**
     * @param begin
     * @param until
     * @param appid
     * @param appidList
     * @return
     */
    public static Criteria queryCriteria(long begin, long until, String appid, List<String> appidList) {
        Criteria criteria = queryCriteria(begin, until, appid);
        if (CollectionUtils.isNotEmpty(appidList)) {
            criteria.and("appID").nin(appidList);
        }
        return criteria;
    }

}
