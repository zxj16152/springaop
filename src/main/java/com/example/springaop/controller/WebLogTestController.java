package com.example.springaop.controller;


import com.example.springaop.annotation.ControllerWebLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Web日志测试
 * @author ganchaoyang
 */
@RestController
@RequestMapping("/weblog")
@Api(tags = "Web日志测试相关接口")
public class WebLogTestController {

    @GetMapping("/get-test")
    @ApiOperation("接口日志GET请求测试")
    @ControllerWebLog(name = "GET请求测试接口", intoDb = true)
    public String hello(@RequestParam("name") String name){
        return name;
    }

//    @PostMapping("/post-test")
//    @ApiOperation("接口日志POST请求测试")
//    @ControllerWebLog(name = "接口日志POST请求测试", intoDb = true)
//    @DistributeLock(key = "post_test_#{baseRequest.channel}", timeout = 10)
//    public String  postTest(@RequestBody @Valid BaseRequest baseRequest, BindingResult bindingResult) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return BaseResponse.addResult();
//    }

}
