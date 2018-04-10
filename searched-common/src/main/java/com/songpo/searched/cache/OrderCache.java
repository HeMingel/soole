package com.songpo.searched.cache;

import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlProduct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OrderCache extends BaseCache<SlOrder> {

    public OrderCache(RedisTemplate<String, SlOrder> redisTemplate) {
        // 订单24小时失效
        super("com.songpo.seached:order:disabled:", redisTemplate, 86400L, TimeUnit.SECONDS, true);
    }
}
