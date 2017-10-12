package com.example.stream.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.Date;

//App2是App1应用中input通道的生产者以及output通道的消费者
@EnableBinding(Processor.class)
public class App2 {
    private static Logger logger= LoggerFactory.getLogger(App2.class);

    @Bean
    @InboundChannelAdapter(value = Processor.OUTPUT,poller = @Poller(fixedDelay ="2000"))
    public MessageSource<Date> timerMessageSource(){
        return ()->new GenericMessage<Date>(new Date());
    }

    @StreamListener(Processor.INPUT)
    public void receiveFromOutput(Object payload){
        logger.info("Received : "+payload);
    }
}
