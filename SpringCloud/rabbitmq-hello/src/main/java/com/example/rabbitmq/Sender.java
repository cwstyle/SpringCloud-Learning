package com.example.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbieTemplate;

    public void send(){
        String context="hello " +new Date();
        System.out.println("Sender : "+context);
        this.rabbieTemplate.convertAndSend("hello",context);
    }
}
