package com.songpo.searched.service;


import com.songpo.searched.entity.SlProductCommentImage;
import com.songpo.searched.mapper.SlProductCommentImageMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentImageService extends BaseService<SlProductCommentImage, String> {

    public ProductCommentImageService(SlProductCommentImageMapper mapper) {
        super(mapper);
    }
}

