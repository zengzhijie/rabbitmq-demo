package com.example.rabbitmqdemo;

import com.example.rabbitmqdemo.provider.PublishProvider;
import com.example.rabbitmqdemo.provider.SimpleProvider;
import com.example.rabbitmqdemo.provider.TopicProvider;
import com.example.rabbitmqdemo.provider.WorkProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Autowired
    private SimpleProvider simpleProvider;

    @Autowired
    private WorkProvider workProvider;

    @Autowired
    private PublishProvider PublishProvider;

    @Autowired
    private TopicProvider topicProvider;


    @Test
     void simpleTest() {
        simpleProvider.send();
    }


    @Test
    void WorkTest() {
        for (int i = 0; i < 100; i++) {
            workProvider.send(i);
        }

    }

    @Test
    void publishTest() {
        PublishProvider.send();
    }

    @Test
    void topicTest() {
        topicProvider.send1();
        topicProvider.send2();
    }

    @Test
    void contextLoads() {
    }

}
