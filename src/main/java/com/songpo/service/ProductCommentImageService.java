package com.songpo.service;


import com.songpo.entity.SlProductCommentImage;
import com.songpo.mapper.SlProductCommentImageMapper;;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentImageService extends BaseService<SlProductCommentImage, String> {

    public ProductCommentImageService(SlProductCommentImageMapper mapper) {
        super(mapper);
    }
}

