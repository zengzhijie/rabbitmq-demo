package com.example.rabbitmqdemo.provider;

import com.example.rabbitmqdemo.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: rabbitmq-demo
 * @description: 发布订阅
 * @author: jack
 * @create: 2021-06-15 11:50
 */
@Slf4j
@Component
@RestController
public class TopicProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendtopic1")
    public void send1() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String context = "Topic1 "+date;
        //简单队列情况下routingkey即为Q名
        this.rabbitTemplate.convertAndSend(Constants.BOOT_EXCHANGE_TOPIC,"topic.message.x.y",context);
        log.info("Topic1 生产者发送消息成功{}",context);

    }

    @GetMapping("/sendtopic2")
    public void send2() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String context = "Topic2 "+date;
        //简单队列情况下routingkey即为Q名
        this.rabbitTemplate.convertAndSend(Constants.BOOT_EXCHANGE_TOPIC,"topic.message.x",context);
        log.info("Topic2 生产者发送消息成功{}",context);

    }

}
