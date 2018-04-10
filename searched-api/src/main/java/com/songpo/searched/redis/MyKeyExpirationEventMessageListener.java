package com.songpo.searched.redis;

import com.songpo.searched.service.CmOrderService;
import com.songpo.searched.service.CmProductService;
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

    private CmProductService cmProductService;

    private CmOrderService cmOrderService;

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
    }

    public CmProductService getCmProductService() {
        return cmProductService;
    }

    public void setCmProductService(CmProductService cmProductService) {
        this.cmProductService = cmProductService;
    }

    public CmOrderService getCmOrderService() {
        return cmOrderService;
    }

    public void setCmOrderService(CmOrderService cmOrderService) {
        this.cmOrderService = cmOrderService;
    }
}
