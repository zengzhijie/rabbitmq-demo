package com.example.rabbitmqdemo.provider;


import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

/**
 * @program: rabbitmq-demo
 * @description:
 * @author: jack
 * @create: 2021-06-15 11:31
 */

public class RabbitmqProducer {
    // 简单模式
    public static final String SIMPLY_QUEUE_KEY = "SIMPLY_QUEUE_KEY";


    public final static String PUBLISH_EXCHANGE_NAME = "PUBLISH_EXCHANGE_FANOUT";

    public final static String DIRECT_EXCHANGE_NAME = "DIRECT_EXCHANGE_FANOUT";

    public final static String TOPIC_EXCHANGE_NAME = "TOPIC_EXCHANGE_FANOUT";



    /**
     * 简单队列模式 生产者
     *
     * 生产者将消息发送到队列，消费者从队列中获取消息
     * @throws Exception
     */
    @Test
    public  void simpleTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(SIMPLY_QUEUE_KEY, false, false, false, null);
        //消息内容
        for (int i = 0; i < 10; i++) {
            String message = "Hello World+" + i;
            channel.basicPublish("",SIMPLY_QUEUE_KEY, null, message.getBytes());
            System.out.println("send:" + message);
            Thread.sleep(10);
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    /**
     * 订阅模式 生产者
     *
     * 1个生产者-多个消费者
     * 每个消费者都有自己的一个队列
     * 生产者没有将消息直接发送给队列，而是发送到了交换机
     * 每个队列都要绑定到交换机
     * 生产者发送的消息，经过交换机到达队列，实现一个消息被多个消费者获取的目的。
     * 一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费。
     *
     * @throws Exception
     */
    @Test
    public  void publishTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //声明exchange
        channel.exchangeDeclare(PUBLISH_EXCHANGE_NAME, "fanout");
        //消息内容
        String message = "Hello World";
        channel.basicPublish(PUBLISH_EXCHANGE_NAME,"",  null, message.getBytes());
        System.out.println("send:" + message);
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    /**
     * 路由模式 生产者
     *
     * 跟订阅模式类似，只不过在订阅模式的基础上加上了类型，订阅模式是分发到所有绑定到交换机的队列，路由模式只分发到绑定在交换机上面指定路由键的队列。
     * 生产者申明一个direct类型交换机，然后发送消息到这个交换机指定路由键。
     * 消费者指定消费这个交换机的这个路由键，即可接收到消息，其他消费者收不到
     * @throws Exception
     */
    @Test
    public  void directTest() throws Exception {
        // 获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明exchange  direct为交换机类型
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct");
        // 消息内容
        String message = "Hello World";
        channel.basicPublish(DIRECT_EXCHANGE_NAME,"",  null, message.getBytes());
        System.out.println("send:" + message);
        //关闭通道和连接
        channel.close();
        connection.close();
    }


    /**
     *  主题模式（通配符模式）
     *
     *  将路由键和某模式进行匹配
     * 队列绑定到一个模式上， #匹配一个或者多个词，*匹配一个词
     * @throws Exception
     */
    @Test
    public  void topicTest() throws Exception {
        // 获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明exchange topic为交换机类型
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");
        // 消息内容
        String message = "Hello World";
        //消息key为 routingtest
        channel.basicPublish(TOPIC_EXCHANGE_NAME,"topic.notice.all",  null, message.getBytes());
        System.out.println("send:" + message);
        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
