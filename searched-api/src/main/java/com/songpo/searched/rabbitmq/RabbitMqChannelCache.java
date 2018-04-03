package com.songpo.searched.rabbitmq;

import com.songpo.searched.cache.BaseCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 刘松坡
 */
@Component
public class RabbitMqChannelCache extends BaseCache<String> {

    public RabbitMqChannelCache(RedisTemplate<String, String> redisTemplate) {
        super("com.songpo.seached:rabbitmq:channel:", redisTemplate);
    }
}
