package com.songpo.service;


import com.songpo.entity.SlProductType;
import com.songpo.entity.SlShop;
import com.songpo.mapper.SlProductTypeMapper;
import com.songpo.mapper.SlShopMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService extends BaseService<SlProductType,String>{

    public ProductTypeService(SlProductTypeMapper mapper) {
        super(mapper);
    }
}
