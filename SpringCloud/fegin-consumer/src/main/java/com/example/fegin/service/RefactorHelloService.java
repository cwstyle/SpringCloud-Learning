package com.example.fegin.service;

import com.example.demo.servive.*;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("hello-service")
public interface RefactorHelloService  extends com.example.demo.servive.HelloService{
}
