package com.songpo.searched.service;

import com.songpo.searched.entity.SlProductTypeTags;
import com.songpo.searched.mapper.SlProductTypeTagsMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeTagsService extends BaseService<SlProductTypeTags, String> {

    public ProductTypeTagsService(SlProductTypeTagsMapper mapper) {
        super(mapper);
    }
}
