package com.example.demo.controller;

import com.example.demo.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
public class HelloController {
    private final Logger logger= Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    private String index() throws Exception{
        ServiceInstance instance=client.getLocalServiceInstance();
        //测试超时
//        int  sleepTime=new Random().nextInt(3000);
//        logger.info("sleepTime:"+sleepTime);
//        Thread.sleep(sleepTime);

        logger.info("/hello,host:"+instance.getHost()+", service_id:"+instance.getServiceId());
        return "Hello World";
    }

    @RequestMapping("/hello1")
    public String hello(@RequestParam("name") String name){
        return "Hello "+name;
    }
    @RequestMapping("/hello2")
    public User hello(@RequestHeader("name") String name ,@RequestHeader("age") Integer age){
        return new User(name,age);
    }
    @RequestMapping("/hello3")
    public String hello(@RequestBody User user){
        return "Hello "+user.getName()+","+user.getAge();
    }
}
