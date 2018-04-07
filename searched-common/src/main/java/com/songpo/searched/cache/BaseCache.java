package com.songpo.searched.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基础缓存类
 *
 * @author 刘松坡
 * @param <V> 缓存的值
 */
@Slf4j
public class BaseCache<V> {

    /**
     * 缓存前缀
     */
    private String prefix;

    /**
     * Redis模板
     */
    private RedisTemplate<String, V> redisTemplate;

    /**
     * 无限制时间缓存
     *
     * @param prefix        前缀
     * @param redisTemplate 模板
     */
    public BaseCache(String prefix, RedisTemplate<String, V> redisTemplate) {
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
    }

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
        try {
            redisTemplate.opsForValue().set(storeKey, value);
        } catch (Exception e) {
            log.error("添加缓存失败，key = {}", storeKey);
        }
    }

    /**
     * 添加缓存-指定缓存周期
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, V value, Long timeOut, TimeUnit timeUnit) {
        String storeKey = prefix + key;
        try {
            redisTemplate.opsForValue().set(storeKey, value, timeOut, timeUnit);
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
     * 是否存在指定键
     *
     * @param key 键
     * @return 结果
     */
    public Boolean hasKey(String key) {
        String deleteKey = prefix + key;
        try {
            return redisTemplate.hasKey(prefix + key);
        } catch (Exception e) {
            log.error("删除缓存失败，key = {}", deleteKey);
        }
        return false;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public RedisTemplate<String, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
