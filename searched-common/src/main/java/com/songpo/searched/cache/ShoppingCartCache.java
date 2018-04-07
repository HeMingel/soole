package com.songpo.searched.cache;

import com.songpo.searched.domain.CMShoppingCart;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartCache extends BaseCache<CMShoppingCart> {

    public ShoppingCartCache(RedisTemplate<String, CMShoppingCart> redisTemplate) {
        super("com.songpo.seached:shoppingCart:", redisTemplate);
    }
}
