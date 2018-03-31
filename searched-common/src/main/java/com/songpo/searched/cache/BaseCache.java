package com.songpo.searched.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基础缓存类
 *
 * @param <V> 缓存的值
 */
@Slf4j
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
        String queryKey = prefix + key;
        try {
            return redisTemplate.opsForValue().get(queryKey);
        } catch (Exception e) {
            log.error("查询缓存失败，key = {}", queryKey);
        }
        return null;
    }

    /**
     * 添加缓存
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, V value) {
        String storeKey = prefix + key;
        // 如果启用缓存周期限制，则根据设置的周期进行保持，否则不进行缓存周期设置
        try {
            if (limitTime) {
                redisTemplate.opsForValue().set(storeKey, value, timeOut, timeUnit);
            } else {
                redisTemplate.opsForValue().set(storeKey, value);
            }
        } catch (Exception e) {
            log.error("添加缓存失败，key = {}", storeKey);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void evict(String key) {
        String deleteKey = prefix + key;
        try {
            redisTemplate.delete(deleteKey);
        } catch (Exception e) {
            log.error("删除缓存失败，key = {}", deleteKey);
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        try {
            redisTemplate.delete(prefix);
        } catch (Exception e) {
            log.error("清空缓存失败，key = {}", prefix);
        }
    }
}
