package com.songpo.searched.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘松坡
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 系统公告广播队列
     *
     * @return 广播队列
     */
    @Bean
    public TopicExchange notificationTopic() {
        return new TopicExchange("notificationTopic");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }


}
