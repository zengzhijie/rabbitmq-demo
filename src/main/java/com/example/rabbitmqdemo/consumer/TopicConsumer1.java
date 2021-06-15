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
@RabbitListener(queues = Constants.BOOT_QUEUE_TOPIC_A,containerFactory = "workListenerFactory")
public class TopicConsumer1 {


    @RabbitHandler
    public void process(byte[] msg){
        System.out.println("TopicConsumer1->receiver:"+ msg);
        log.info("TopicConsumer1 消费者接收消息{}",msg);
    }





}
