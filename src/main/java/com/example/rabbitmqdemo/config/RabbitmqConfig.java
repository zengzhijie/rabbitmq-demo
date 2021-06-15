package com.example.rabbitmqdemo.config;

import com.example.rabbitmqdemo.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq-demo
 * @description:
 * @author: jack
 * @create: 2021-06-15 10:57
 */
@Configuration
public class RabbitmqConfig {

    // 简单模式
    public static final String SIMPLY_QUEUE_KEY = "SIMPLY_QUEUE_KEY";

    // 交换机
    public static final String DE_QUEUE_KEY = "DE_QUEUE_KEY";

    public static final String DR_QUEUE_KEY = "DR_QUEUE_KEY";

    public static final String DQ_QUEUE_KEY = "DQ_QUEUE_KEY";


    //简单模式
    @Bean
    public Queue queue() {

        return new Queue(Constants.BOOT_QUEUE_SIMPLE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }


    //工作模式
    @Bean
    public Queue queueWork() {

        return new Queue(Constants.BOOT_QUEUE_WORK);
    }
    @Bean("workListenerFactory")
    public RabbitListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        //自动ack,没有异常情况下自动发送ack
        //auto 自动确认，默认是Auto
        //MANUAL 手动确认
        //none 不确认，发完自动丢弃
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //拒绝策略，true回到队列，false是丢弃，默认为true
        containerFactory.setDefaultRequeueRejected(true);
        //默认的prefetchCount是250，效率低
        //设置为1
        containerFactory.setPrefetchCount(1);
        return containerFactory;
    }

    //订阅模式
    //队列
    @Bean
    public Queue aPublishQueue(){
        return new Queue(Constants.A_BOOT_QUEUE_PUBLISH);
    }
    @Bean
    public Queue bPublishQueue(){
        return new Queue(Constants.B_BOOT_QUEUE_PUBLISH);
    }
    @Bean
    public Queue cPublishQueue(){
        return new Queue(Constants.C_BOOT_QUEUE_PUBLISH);
    }
    //交换机
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(Constants.BOOT_EXCHANGE_PUBLISH);
    }
    //绑定交换机
    @Bean
    Binding bindingExchangeA(Queue aPublishQueue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(aPublishQueue).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue bPublishQueue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(bPublishQueue).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(Queue cPublishQueue,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(cPublishQueue).to(fanoutExchange);
    }

    //topic模式

    //队列
    @Bean
    Queue topicQueueA(){
        return new Queue(Constants.BOOT_QUEUE_TOPIC_A);
    }
    @Bean
    Queue topicQueueB(){
        return new Queue(Constants.BOOT_QUEUE_TOPIC_B);
    }
    //topic交换机
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(Constants.BOOT_EXCHANGE_TOPIC);
    }
    //绑定交换机
    @Bean
    Binding bindingTopicExchangeA(Queue topicQueueA,TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with("topic.message.*");
    }
    @Bean
    Binding bindingTopicExchangeB(Queue topicQueueB,TopicExchange topicExchange){
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with("topic.message.#");
    }






}
