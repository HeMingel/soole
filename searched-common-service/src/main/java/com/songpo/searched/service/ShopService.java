package com.songpo.searched.service;


import com.songpo.searched.entity.SlShop;
import com.songpo.searched.mapper.SlShopMapper;
import org.springframework.stereotype.Service;

@Service
public class ShopService extends BaseService<SlShop, String> {

    public ShopService(SlShopMapper mapper) {
        super(mapper);
    }
}
