package com.songpo.searched.service;

import com.songpo.searched.domain.MyShoppingCartPojo;
import com.songpo.searched.mapper.SlTagNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyShoppingCartService {

    @Autowired
    private RedisTemplate<String, MyShoppingCartPojo> redisTemplate;

    @Autowired
    private SlTagNameMapper slTagNameMapper;

    public String findTagNameByTagId(String tagId, String goodId){
        return this.slTagNameMapper.findTagNameByTagId(tagId,goodId);
    };
}
