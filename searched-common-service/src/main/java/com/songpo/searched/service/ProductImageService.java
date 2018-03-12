package com.songpo.searched.service;


import com.songpo.searched.entity.SlProductImage;
import com.songpo.searched.mapper.SlProductImageMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService extends BaseService<SlProductImage, String> {

    public ProductImageService(SlProductImageMapper mapper) {
        super(mapper);
    }
}

