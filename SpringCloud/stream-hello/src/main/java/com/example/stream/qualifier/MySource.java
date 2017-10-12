package com.example.stream.qualifier;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {
    @Output("Output-1")
    MessageChannel output1();

    @Output("Output-2")
    MessageChannel output2();
}
