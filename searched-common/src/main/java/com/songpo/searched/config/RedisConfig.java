package com.songpo.searched.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

//    /**
//     * 购物车模板
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, CMShoppingCart> myShoppingCartTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, CMShoppingCart> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(CMShoppingCart.class));
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
//    /**
//     * 商品模板
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, SlProductRepository> productRepositoryTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, SlProductRepository> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SlProductRepository.class));
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
//    /**
//     * 商品显示活动模板
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, SlProduct> productTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, SlProduct> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SlProduct.class));
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
//    /**
//     * 订单模板
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, SlOrder> orderTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, SlOrder> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SlOrder.class));
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
//    /**
//     * 用户缓存模板
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, SlUser> userRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, SlUser> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SlUser.class));
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }

}
