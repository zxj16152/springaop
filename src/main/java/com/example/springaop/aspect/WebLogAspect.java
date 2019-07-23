package com.example.springaop.aspect;

import com.alibaba.fastjson.JSON;
import com.example.springaop.annotation.ControllerWebLog;
import com.example.springaop.model.WebLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description TODO
 * @Author zhouxinjian
 * @Date 2019-07-10 16:34
 **/

@Aspect
@Component
@Order(100)
public class WebLogAspect {
    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String START_TIME = "startTime";

    private static final String REQUEST_PARAMS = "requestParams";

//    @Pointcut("execution(* com.example.springaop.controller..*.*(..))")
//    public void webLog() {}
    @Pointcut("@annotation(com.example.springaop.annotation.ControllerWebLog)")
    public void webLog() {}


    @Before(value = "webLog()&&@annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        // 请求参数。
        StringBuilder requestStr = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                requestStr.append(arg.toString());
            }
        }
        threadInfo.put(REQUEST_PARAMS, requestStr.toString());
        threadLocal.set(threadInfo);
        logger.info("{}接口开始调用:requestData={}", controllerWebLog.name(), threadInfo.get(REQUEST_PARAMS));
    }

    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "res")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object res) {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        if (controllerWebLog.intoDb()) {
            insertResult(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""), JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        logger.info("{}接口结束调用:耗时={}ms,result={}", controllerWebLog.name(), takeTime, res);
    }

    public void insertResult(String operationName, String requestStr, String responseStr, long takeTime) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(false);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setResponse(responseStr);
        webLog.setTaketime(takeTime);
        System.out.println("============"+webLog);
//        webLogMapper.insert(webLog);
    }


    public void insertError(String operationName, String requestStr, Throwable throwable) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(true);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setStack(throwable.getStackTrace().toString());
//        webLogMapper.insert(webLog);
    }
}
