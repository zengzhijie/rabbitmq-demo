package com.example.rabbitmqdemo.consumer;

import com.example.rabbitmqdemo.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @program: rabbitmq-demo
 * @description:
 * @author: jack
 * @create: 2021-06-15 11:31
 */

public class RabbitmqCosumer {
    /**
     * 简单队列模式 消费者
     * @throws Exception
     */


    // 简单模式
    public static final String SIMPLY_QUEUE_KEY = "SIMPLY_QUEUE_KEY";


    public final static String PUBLISH_EXCHANGE_NAME = "PUBLISH_EXCHANGE_FANOUT";

    public final static String DIRECT_EXCHANGE_NAME = "DIRECT_EXCHANGE_FANOUT";

    public final static String TOPIC_EXCHANGE_NAME = "TOPIC_EXCHANGE_FANOUT";


    @Test
    public  void simpleTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(SIMPLY_QUEUE_KEY, false, false, false, null);
        //获取消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println("[Receive]：" + message);
            }
        };
        channel.basicConsume(SIMPLY_QUEUE_KEY, consumer);
    }


    //消费者1
    private final static String PUBLISH_QUEUE_NAME_1 = "PUBLISH_QUEUE_1";

    //消费者2
    private final static String PUBLISH_QUEUE_NAME_2 = "PUBLISH_QUEUE_2";

    /**
     * 订阅模式 消费者1
     * @throws Exception
     */
    @Test
    public  void publishTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(PUBLISH_QUEUE_NAME_1, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(PUBLISH_QUEUE_NAME_1, PUBLISH_EXCHANGE_NAME, "");
        //同一时刻服务器只会发送一条消息
        channel.basicQos(1);
        //获取消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                //表示使用手动确认模式
                System.out.println("[Receive]：" + message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(PUBLISH_QUEUE_NAME_1, false, consumer);
    }


    /**
     * 订阅模式 消费者2
     * @throws Exception
     */
    @Test
    public  void publishTest2() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(PUBLISH_QUEUE_NAME_2, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(PUBLISH_QUEUE_NAME_2, PUBLISH_EXCHANGE_NAME, "");
        //同一时刻服务器只会发送一条消息
        channel.basicQos(1);
        //获取消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                //表示使用手动确认模式
                System.out.println("[Receive]：" + message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(PUBLISH_QUEUE_NAME_2, false, consumer);
    }

    /**
     * 路由模式 消费者
     * @throws Exception
     */
    @Test
    public  void directTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(PUBLISH_QUEUE_NAME_1, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(PUBLISH_QUEUE_NAME_1, DIRECT_EXCHANGE_NAME, "routingtest");
        //同一时刻服务器只会发送一条消息
        channel.basicQos(1);
        //获取消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                //表示使用手动确认模式
                System.out.println("[Receive]：" + message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(PUBLISH_QUEUE_NAME_1, false, consumer);


    }


    /**
     * 主题模式（通配符模式） 消费者
     * @throws Exception
     */
    @Test
    public  void topicTest() throws Exception {
        //获取连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(PUBLISH_QUEUE_NAME_1, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(PUBLISH_QUEUE_NAME_1, TOPIC_EXCHANGE_NAME, "topic.*");
        //channel.queueBind(TOPIC_QUEUE_NAME_1, TOPIC_EXCHANGE_NAME, "topic.#");
        //同一时刻服务器只会发送一条消息
        channel.basicQos(1);
        //获取消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                //表示使用手动确认模式
                System.out.println("[Receive]：" + message);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(PUBLISH_QUEUE_NAME_1, false, consumer);


    }
}
