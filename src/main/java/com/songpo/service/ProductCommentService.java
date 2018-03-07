package com.songpo.service;


import com.songpo.entity.SlProductComment;
import com.songpo.entity.SlTag;
import com.songpo.mapper.SlProductCommentMapper;
import com.songpo.mapper.SlTagMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentService extends BaseService<SlProductComment, String> {

    public ProductCommentService(SlProductCommentMapper mapper) {
        super(mapper);
    }
}

