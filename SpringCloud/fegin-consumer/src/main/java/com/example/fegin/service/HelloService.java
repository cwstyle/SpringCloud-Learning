package com.example.fegin.service;

import com.example.fegin.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//fallback设置降级处理
@FeignClient(value = "hello-service",fallback = HelloServiceFallback.class)
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    @RequestMapping("/hello1")
    String hello(@RequestParam("name") String name);
    @RequestMapping("/hello2")
    User hello(@RequestHeader("name") String name,@RequestHeader("age") Integer age);
    @RequestMapping("/hello3")
    String hello(@RequestBody User user);
}
