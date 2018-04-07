package com.songpo.searched.redis;

import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author 刘松坡
 */
@Slf4j
public class MyKeyExpirationEventMessageListener extends KeyExpirationEventMessageListener {

    private NotificationService notificationService;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public MyKeyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        log.debug("接收到Redis键失效事件，键：{}", message);
        // 获取到的key
        String key = new String(message.getBody());
        String channel = key.substring(0, key.lastIndexOf(":"));
        String payload = key.substring(channel.length() + 1);

        // 发送消息
        notificationService.sendToQueue("", channel, payload, MessageTypeEnum.SYSTEM);
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
