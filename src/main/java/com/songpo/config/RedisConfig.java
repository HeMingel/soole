package com.songpo.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    public static final String KEY_PREFIX = "com.songpo.searched:";

    @Bean
    public RedisTemplate<String, Object> mybatisRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            String[] value = new String[1];
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            if (cacheable != null) {
                value = cacheable.value();
            }
            CachePut cachePut = method.getAnnotation(CachePut.class);
            if (cachePut != null) {
                value = cachePut.value();
            }
            CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
            if (cacheEvict != null) {
                value = cacheEvict.value();
            }
            sb.append(value[0]);
            for (Object obj : params) {
                sb.append(":").append(obj.toString());
            }
            return sb.toString();
        };
    }
}
