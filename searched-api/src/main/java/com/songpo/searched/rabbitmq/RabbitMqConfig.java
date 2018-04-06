package com.songpo.searched.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 刘松坡
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }


}
