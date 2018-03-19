package com.songpo.searched.config;

import com.songpo.searched.domain.MyShoppingCartPojo;
import com.songpo.searched.entity.SlUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * redis 构造器
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, MyShoppingCartPojo> myShoppingCartTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, MyShoppingCartPojo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MyShoppingCartPojo.class));
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * 用户缓存模板
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, SlUser> userRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, SlUser> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SlUser.class));
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
