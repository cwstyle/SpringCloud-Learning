package com.example.ribbon.controller;

import com.example.ribbon.model.User;
import com.example.ribbon.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@RestController
public class ConsmerController {

    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private DiscoveryClient client;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloConsumer() {
        return helloService.helloService();
    }

    public String helloFallback(){
        return "error";
    }

    @RequestMapping("/helloSleep")
    public String helloConsumerSleep() throws Exception {
        ServiceInstance instance = client.getLocalServiceInstance();
        //让处理现成等待几秒钟
        int sleepTime=new Random().nextInt(3000);
//        int sleepTime = 4000;

        logger.info("sleepTime:" + sleepTime);
        Thread.sleep(sleepTime);

        logger.info("/helloSleep,host:" + instance.getHost() + ",service_id:" + instance.getServiceId());
        return helloService.helloService();
    }

    @RequestMapping("/ribbon-consumer")
    public String hello() {
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class, "dd").getBody();
    }

    //一、使用使用getForEntity
    //传递参数，请求获取String
    @RequestMapping("/test")
    public String test() {
//        1.使用getForEntity(String url, Class<T> responseType, Object... urlVariables)
//        ResponseEntity<String> responseEntity=restTemplate.getForEntity("http://HELLO-SERVICE/user?name={1}",String.class,"didi");
//        String body=responseEntity.getBody();

//        2.使用getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables)
//        url中的占位符用map中的key值
        Map<String, String> params = new HashedMap();
        params.put("name", "dada");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/user?name={name}", String.class, params);
        String body = responseEntity.getBody();
//      3.使用getForEntity(URI url, Class<T> responseType) throws RestClientException {
        return body;
    }

    //传递参数，请求获取User
    @RequestMapping("/getUser")
    public User getUser() {
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/getUser?name={1}", User.class, "didi");
        User user = responseEntity.getBody();
        return user;
    }

    //二、使用getForObject,当不需要关注请求响应出body外的其他内容时使用
    @RequestMapping("test2")
    public String test2() {
        return restTemplate.getForObject("http://HELLO-SERVICE/hello", String.class);
    }

    @RequestMapping("testUser")
    public User testUser() {
        return restTemplate.getForObject("http://HELLO-SERVICE/user?name={1}", User.class, "didi");
    }

    //    三、postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables)
    public User testPost() {
        User user = new User(1l, "dd", "kksks");
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://HELLO-SERVICE/user", user, User.class);
        return responseEntity.getBody();
    }

    //    四、postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
    public User testPost2() {

        User user = new User(1l, "dd", "kksks");
        return restTemplate.postForObject("http://HELLO-SERVICE/user", user, User.class);
    }
}
