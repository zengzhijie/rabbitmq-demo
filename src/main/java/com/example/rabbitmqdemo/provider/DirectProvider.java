package com.example.rabbitmqdemo.provider;

import com.example.rabbitmqdemo.config.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rabbitmq-demo
 * @description: 交换机
 * @author: jack
 * @create: 2021-06-15 11:50
 */
@RestController
public class DirectProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirect")
    public void test() {
        //参数1：队列名称
        //参数2，发送的消息数据
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitmqConfig.SIMPLY_QUEUE_KEY, "this is direct  msg【"+i+"】");
            System.out.println(i+"sendDirect发送消息成功:this is simple msg【"+i+"】");
        }


    }

}
