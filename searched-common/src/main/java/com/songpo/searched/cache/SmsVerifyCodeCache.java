package com.songpo.searched.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 刘松坡
 */
@Component
public class SmsVerifyCodeCache extends BaseCache<String> {

    public SmsVerifyCodeCache(RedisTemplate<String, String> redisTemplate) {
        super("com.songpo.seached:verify-code:", redisTemplate);
    }
}
