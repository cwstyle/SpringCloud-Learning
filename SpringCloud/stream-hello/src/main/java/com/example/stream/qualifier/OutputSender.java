package com.example.stream.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class OutputSender {

    //@Qualifier指定通道名称
    @Autowired @Qualifier("Output-1")
    private MessageChannel output;
}
