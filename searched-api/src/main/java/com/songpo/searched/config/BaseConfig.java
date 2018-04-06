package com.songpo.searched.config;

import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.redis.MyKeyExpirationEventMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Collections;

/**
 * @author 刘松坡
 */
@Configuration
public class BaseConfig {

    @Autowired
    public NotificationService notificationService;

    @Bean
    RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        MyKeyExpirationEventMessageListener messageListener = new MyKeyExpirationEventMessageListener(listenerContainer);
        messageListener.setNotificationService(notificationService);
        listenerContainer.addMessageListener(messageListener, Collections.singleton(new PatternTopic("__keyevent@*__:expired")));
        return listenerContainer;
    }

}
