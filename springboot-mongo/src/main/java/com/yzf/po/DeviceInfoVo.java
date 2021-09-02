/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.yzf.po;

import lombok.Data;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2019/12/6 16:29
 */
@Data
public class DeviceInfoVo {
    private String imei1;
    private String imei2;
    private String androidId;
    private String oaid;
    private String idfa;
    private String idfv;
    private String openudid;
    private String appID;
    /**
     * 手机型号，样例：iPad11,3
     */
    private String brand;
    private int timestamp;

    /**
     * 临时上报类型
     */
    private int tempReport;
}
