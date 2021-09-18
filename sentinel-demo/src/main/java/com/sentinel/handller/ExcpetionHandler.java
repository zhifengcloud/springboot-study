/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.sentinel.handller;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/9/2 16:41
 */
@RestControllerAdvice
public class ExcpetionHandler {


    /**
     * 处理流量控制异常处理
     */
    @ExceptionHandler({FlowException.class})
    public ResponseData handleRRException(FlowException e){
        return new ResponseData(-1, "流控规则被触发...");
    }

    /**
     * 服务降级异常处理
     */
    @ExceptionHandler({DegradeException.class})
    public ResponseData handleRRException(DegradeException e){
        return new ResponseData(-2, "熔断规则被触发...");
    }


//
//    @ExceptionHandler(ParamFlowException.class)
//    public ResponseData test() {
//        return new ResponseData(-3, "热点规则被触发...");
//    }

    /**
     * 处理流量控制异常处理
     */
    @ExceptionHandler({AuthorityException.class})
    public ResponseData handleRRException(AuthorityException e){
        return new ResponseData(-4, "授权规则被触发...");
    }

    /**
     * 处理流量控制异常处理
     */
    @ExceptionHandler({SystemBlockException.class})
    public ResponseData handleRRException(SystemBlockException e){
        return new ResponseData(-5, "系统保护规则被触发...");
    }
}
