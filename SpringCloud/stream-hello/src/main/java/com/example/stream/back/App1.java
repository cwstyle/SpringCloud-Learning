package com.example.stream.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;


@EnableBinding(Processor.class)
public class App1 {
    private static Logger logger= LoggerFactory.getLogger(App1.class);

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)//返回消息
    public  Object receiverFromInput(Object payload){
        logger.info("Received:"+payload);
        return "From Input Channel Return - "+payload;
    }
}
