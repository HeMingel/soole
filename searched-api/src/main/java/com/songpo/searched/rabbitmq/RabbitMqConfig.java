package com.songpo.searched.rabbitmq;

import org.springframework.amqp.core.Queue;
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

    /**
     * 商品秒杀活动频道
     *
     * @return 消息队列
     */
    @Bean
    public Queue productTimeLimitQueue() {
        return new Queue("queue_com.songpo.seached:product:time-limit");
    }


}
