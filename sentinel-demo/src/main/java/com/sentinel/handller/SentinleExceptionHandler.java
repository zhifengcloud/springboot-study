package com.sentinel.handller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义流控返回输出
 */
//@Service
public class SentinleExceptionHandler {
//        implements UrlBlockHandler{
//    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
         //BlockException 异常接口,包含Sentinel的五个异常
        // FlowException 限流异常
        // DegradeException 降级异常
        // ParamFlowException 参数限流异常
        // AuthorityException 授权异常
        // SystemBlockException 系统负载异常
        ResponseData data = new ResponseData();
        if (e instanceof FlowException) {
            data = new ResponseData(999, "访问人数过多,接口被限流了。");
        } else if (e instanceof DegradeException) {
            data = new ResponseData(888, "接口被降级了。");
        }else if (e instanceof AuthorityException) {
            data = new ResponseData(-3, "授权规则被触发...");
//        } else if (e instanceof ParamFlowException) {
//            data = new ResponseData(-4, "热点规则被触发...");
//
        }else if (e instanceof SystemBlockException) {
            data = new ResponseData(-5, "系统规则被触发...");
        }
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(data));
    }
}


/**
 * 定义返回的实体类，字段根据需要添加
 */
@Data       // 生成getter/setter/tostring/equals
@AllArgsConstructor // 全参构造
@NoArgsConstructor
        // 无参构造
class ResponseData {
    private int code;
    private String message;
}


