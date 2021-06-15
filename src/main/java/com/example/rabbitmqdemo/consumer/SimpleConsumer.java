package com.example.rabbitmqdemo.consumer;

import com.example.rabbitmqdemo.config.RabbitmqConfig;
import com.example.rabbitmqdemo.utils.Constants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
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
@RabbitListener(queues = Constants.BOOT_QUEUE_SIMPLE)
public class SimpleConsumer {



    @RabbitHandler
    public void process(String msg){
        //接收消息
        System.out.println("SimpleConsumer->receiver:"+ msg);
        log.info("SimpleConsumer消费者接收消息{}",msg);
    }





//    /**
//     * simple消费者
//     *    参数@Queue：队列：
//     *       参数value：队列名称，不填则使用临时队列
//     *       参数declare：是否持久化，默认为true
//     *       参数exclusive：是否是独占队列，默认为false
//     *       参数autoDelete：是否自动删除，默认为false
//     * @param msg
//     */
//    @RabbitListener(queuesToDeclare = @Queue(value = RabbitmqConfig.SIMPLY_QUEUE_KEY))
//    public void receiveMessage(String msg, Channel channel, Message message) {
//        // 只包含发送的消息
//        System.out.println("1接收到消息：" + msg);
//        // channel 通道信息
//        // message 附加的参数信息
//    }
//
//    @RabbitListener(queuesToDeclare = @Queue(value = RabbitmqConfig.SIMPLY_QUEUE_KEY))
//    public void receiveMessage2(Object obj, Channel channel, Message message) {
//        // 包含所有的信息
//        System.out.println("2接收到消息：" + obj);
//    }

}
