package com.songpo.searched.cache;

import com.songpo.searched.entity.SlProduct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductCache extends BaseCache<SlProduct> {

    public ProductCache(RedisTemplate<String, SlProduct> redisTemplate) {
        super("com.songpo.seached:product:", redisTemplate);
    }
}
