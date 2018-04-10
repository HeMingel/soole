package com.songpo.searched.redis;

import com.songpo.searched.service.CmOrderService;
import com.songpo.searched.service.CmProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author 刘松坡
 */
@Slf4j
public class MyKeyExpirationEventMessageListener extends KeyExpirationEventMessageListener {

    /**
     * 限时秒杀KEY
     */
    public static final String PRODUCT_TIME_LIMIT = "com.songpo.seached:product:time-limit";
    @Autowired
    private CmProductService cmProductService;
    @Autowired
    private CmOrderService cmOrderService;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public MyKeyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 接收Redis Key失效事件
     *
     * @param message 消息内容
     */
    @Override
    protected void doHandleMessage(Message message) {
        log.debug("接收到Redis键失效事件，键：{}", message);
        // 获取到的key
        String key = new String(message.getBody());

        // 处理限时秒杀商品
        if (key.startsWith(PRODUCT_TIME_LIMIT)) {
            this.cmProductService.processProductUndercarriage(key);
        }
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
