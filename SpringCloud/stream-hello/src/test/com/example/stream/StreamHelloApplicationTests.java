package com.example.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StreamHelloApplication.class)
@WebAppConfiguration
public class StreamHelloApplicationTests {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void contextLoads(){
        sinkSender.output().send(MessageBuilder.withPayload("From SinkSender").build());
    }
}

