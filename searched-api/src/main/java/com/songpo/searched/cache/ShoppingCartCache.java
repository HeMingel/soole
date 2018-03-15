package com.songpo.searched.cache;

import com.songpo.searched.domain.MyShoppingCartPojo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ShoppingCartCache extends BaseCache<MyShoppingCartPojo> {

    public ShoppingCartCache(RedisTemplate<String, MyShoppingCartPojo> redisTemplate) {
        super("com.songpo.seached:shoppingCart:", redisTemplate, 7200L, TimeUnit.SECONDS, true);
    }
}
