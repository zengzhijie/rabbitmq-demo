package com.example.rabbitmqdemo.provider;

import com.example.rabbitmqdemo.config.RabbitmqConfig;
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
 * @description: 建单队列模式
 * @author: jack
 * @create: 2021-06-15 11:50
 */
@Slf4j
@Component
@RestController
public class SimpleProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @GetMapping("/sendSimple")
//    public void test() {
//        //参数1：队列名称
//        //参数2，发送的消息数据
//        for (int i = 0; i < 10; i++) {
//            rabbitTemplate.convertAndSend(RabbitmqConfig.SIMPLY_QUEUE_KEY, "this is simple msg【"+i+"】");
//            System.out.println(i+"sendDirect发送消息成功:this is simple msg【"+i+"】");
//        }
//
//    }
    @GetMapping("/sendSimple")
    public void send() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String context = "hello "+date;
        System.out.println("SimpleProvider->sender :"+ context);
        //简单队列情况下routingkey即为Q名
        this.rabbitTemplate.convertAndSend(Constants.BOOT_QUEUE_SIMPLE,context);
        log.info("SimpleProvider 生产者发送消息成功{}",context);

    }

}
