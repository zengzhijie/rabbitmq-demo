package com.example.rabbitmqdemo.consumer;

import com.example.rabbitmqdemo.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq-demo
 * @description:
 * @author: jack
 * @create: 2021-06-15 12:55
 */
@Slf4j
@Component
@RabbitListener(queues = Constants.C_BOOT_QUEUE_PUBLISH)
public class PublishConsumer3 {


    @RabbitHandler
    public void process(String msg){
        System.out.println("PublishConsumer3->receiver:"+ msg);
        log.info("PublishConsumer3 消费者接收消息{}",msg);
    }





}
