package com.example.rabbitmqdemo.consumer;

import com.example.rabbitmqdemo.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq-demo
 * @description:
 * @author: jack
 * @create: 2021-06-15 12:55
 */
@Component
public class DirectConsumer {

    /**
     * direct消费者
     *    direct消费者需要bind绑定交换机
     *     QueueBinding参数：
     *     value绑定队列名，如果不声明名称，则创建临时队列，
     *     exchange绑定交换机，type指定路由模式的类型（direct或topic），默认direct,name为交换机名称，不指定则使用默认交换机
     *     key为路由key，可用{}绑定多个路由key

     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(RabbitmqConfig.DQ_QUEUE_KEY),
            exchange = @Exchange(
                    type = "direct",
                    name = RabbitmqConfig.DE_QUEUE_KEY),
            key = {RabbitmqConfig.DR_QUEUE_KEY}))
    public void receiveMessage(String msg) {
        // 只包含发送的消息
        System.out.println("1接收到消息：" + msg);
        // channel 通道信息
        // message 附加的参数信息
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(RabbitmqConfig.DQ_QUEUE_KEY),
            exchange = @Exchange(
                    type = "direct",
                    name = RabbitmqConfig.DE_QUEUE_KEY),
            key = {RabbitmqConfig.DR_QUEUE_KEY}))
    @RabbitListener(queuesToDeclare = @Queue(value = RabbitmqConfig.SIMPLY_QUEUE_KEY))
    public void receiveMessage2(Object obj) {
        // 包含所有的信息
        System.out.println("2接收到消息：" + obj);
    }

}
