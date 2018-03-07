package com.songpo.service;


import com.songpo.entity.SlProductImage;
import com.songpo.entity.SlTag;
import com.songpo.mapper.SlProductImageMapper;
import com.songpo.mapper.SlTagMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService extends BaseService<SlProductImage, String> {

    public ProductImageService(SlProductImageMapper mapper) {
        super(mapper);
    }
}

