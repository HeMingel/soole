package com.songpo.searched.cache;

import com.songpo.searched.domain.CMShoppingCart;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ShoppingCartCache extends BaseCache<CMShoppingCart> {

    public ShoppingCartCache(RedisTemplate<String, CMShoppingCart> redisTemplate) {
        super("com.songpo.seached:shoppingCart:", redisTemplate, 7200L, TimeUnit.SECONDS, true);
    }
}
