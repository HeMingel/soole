//package com.songpo.searched.cache;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class ProductCache extends BaseCache<SlProduct> {
//
//    public ProductCache(RedisTemplate<String, SlProduct> redisTemplate) {
//        super("com.songpo.seached:product:time-limit:", redisTemplate, 7200L, TimeUnit.SECONDS);
//    }
//
//}
