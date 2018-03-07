package com.songpo.service;


import com.songpo.entity.SlShop;
import com.songpo.mapper.SlShopMapper;
import org.springframework.stereotype.Service;

@Service
public class ShopService extends BaseService<SlShop,String>{

    public ShopService(SlShopMapper mapper) {
        super(mapper);
    }
}
