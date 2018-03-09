package com.songpo.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基础缓存类
 *
 * @param <V> 缓存的值
 */
@Data
@AllArgsConstructor
public class BaseCache<V> {

    public String prefix;

    public RedisTemplate<String, V> redisTemplate;

    /**
     * 缓存有效期
     */
    public Long timeOut = 7L;

    /**
     * 缓存有效期单位
     */
    public TimeUnit timeUnit = TimeUnit.DAYS;

    /**
     * 是否为缓存设置有效期
     */
    public Boolean limitTime = true;

    public V get(String key) {
        return redisTemplate.opsForValue().get(prefix + key);
    }

    public void put(String key, V value) {
        if (limitTime) {
            redisTemplate.opsForValue().set(prefix + key, value, timeOut, timeUnit);
        } else {
            redisTemplate.opsForValue().set(prefix + key, value);
        }
    }

    public void evict(String key) {
        redisTemplate.delete(prefix + key);
    }

    public void clear() {
        redisTemplate.delete(prefix);
    }
}
