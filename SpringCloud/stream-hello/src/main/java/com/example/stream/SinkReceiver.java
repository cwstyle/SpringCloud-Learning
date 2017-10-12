package com.example.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

//指定一个过多个定义了@Input或@Output注解的接口，以此实现对消息通道(Channerl)的绑定
//sink接口默认是实现对输入通道绑定的定义
@EnableBinding(value = {Sink.class,SinkSender.class})//还可以用Source和Processor
public class SinkReceiver {
    private static Logger logger= LoggerFactory.getLogger(StreamHelloApplication.class);

//将被修饰的方法注册为消息中间件上数据流的事件监听器，注解中的属性值对应了监听的消息通道名
    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        logger.info("Received:"+payload);
    }
}
