package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.MyShoppingCartPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyShoppingCartService {

    @Autowired
    private RedisTemplate<String, MyShoppingCartPojo> redisTemplate;

    /**
     * 添加购物车商品
     * @param pojo
     */
    public void addmyShoppingCart(MyShoppingCartPojo pojo) {
        BusinessMessage businessMessage = new BusinessMessage();
        this.redisTemplate.opsForValue().set("myShoppingCart", pojo);
    }
}
