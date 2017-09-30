package com.example.fegin.controller;

import com.example.fegin.model.User;
import com.example.fegin.service.HelloService;
import com.example.fegin.service.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CosumerController {

    @Autowired
    HelloService helloService;

    @Autowired
    RefactorHelloService refactorHelloService;
    @RequestMapping("/feign-consumer")
    public String helloConsumer(){
        return helloService.hello();
    }

    @RequestMapping("/feign-consumer2")
    public String helloConsumer2(){
        StringBuilder sb=new StringBuilder();
        sb.append(helloService.hello()).append("\n");
        sb.append(helloService.hello("DIDI")).append("\n");
        sb.append(helloService.hello("DIDI",30)).append("\n");
        sb.append(helloService.hello(new User("DD",33))).append("\n");
        return sb.toString();
    }

    @RequestMapping("/feign-consumer3")
    public String helloConsumer3(){
        StringBuilder sb=new StringBuilder();
        sb.append(refactorHelloService.hello("MM")).append("\n");
        sb.append(refactorHelloService.hello("MIMI",30)).append("\n");
        sb.append(refactorHelloService.hello(new com.example.demo.model.User("DD",33))).append("\n");
        return sb.toString();
    }

}
