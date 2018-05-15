package com.songpo.searched.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘松坡
 */
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.channelname}")
    private String topicGlobalMessage;

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }

    /**
     * 商品秒杀活动频道
     *
     * @return 消息队列
     */
    @Bean
    public Queue productTimeLimitQueue() {
        return new Queue("queue_com.songpo.seached:product:time-limit");
    }

    /**
     * 订单频道
     *
     * @return 消息队列
     */
    @Bean
    public Queue orderDisabledQueue() {
        return new Queue("queue_com.songpo.seached:order:disabled");
    }

    /**
     * 全局消息交换机
     *
     * @return
     */
    @Bean
    public TopicExchange globalTopicExchange() {
        return new TopicExchange(topicGlobalMessage);
    }

}
