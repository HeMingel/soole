package com.songpo.searched.service;


import com.songpo.searched.entity.SlProductComment;
import com.songpo.searched.mapper.SlProductCommentMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentService extends BaseService<SlProductComment, String> {

    public ProductCommentService(SlProductCommentMapper mapper) {
        super(mapper);
    }
}

