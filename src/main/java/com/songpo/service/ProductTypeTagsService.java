package com.songpo.service;

import com.songpo.entity.SlProductTypeTags;
import com.songpo.mapper.SlProductTypeTagsMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeTagsService extends BaseService<SlProductTypeTags, String> {

    public ProductTypeTagsService(SlProductTypeTagsMapper mapper) {
        super(mapper);
    }
}
