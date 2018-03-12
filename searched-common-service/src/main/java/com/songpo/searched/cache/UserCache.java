package com.songpo.searched.cache;

import com.songpo.searched.entity.SlUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserCache extends BaseCache<SlUser> {

    public UserCache(RedisTemplate<String, SlUser> redisTemplate) {
        super("com.songpo.seached:user:", redisTemplate, 7200L, TimeUnit.SECONDS, true);
    }
}
