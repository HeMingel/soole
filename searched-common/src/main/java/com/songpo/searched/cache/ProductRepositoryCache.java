package com.songpo.searched.cache;
import com.songpo.searched.entity.SlProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProductRepositoryCache extends BaseCache<SlProductRepository> {

    public ProductRepositoryCache(RedisTemplate<String, SlProductRepository> redisTemplate) {
        super("com.songpo.seached:repository:", redisTemplate, 7200L, TimeUnit.SECONDS, true);
    }
}
