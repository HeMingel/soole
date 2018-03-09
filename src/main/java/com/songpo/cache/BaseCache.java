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

    /**
     * 缓存前缀
     */
    public String prefix;

    /**
     * Redis模板
     */
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

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public V get(String key) {
        return redisTemplate.opsForValue().get(prefix + key);
    }

    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     */
    public void put(String key, V value) {
        // 如果启用缓存周期限制，则根据设置的周期进行保持，否则不进行缓存周期设置
        if (limitTime) {
            redisTemplate.opsForValue().set(prefix + key, value, timeOut, timeUnit);
        } else {
            redisTemplate.opsForValue().set(prefix + key, value);
        }
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public void evict(String key) {
        redisTemplate.delete(prefix + key);
    }

    /**
     * 清空缓存
     */
    public void clear() {
        redisTemplate.delete(prefix);
    }
}
