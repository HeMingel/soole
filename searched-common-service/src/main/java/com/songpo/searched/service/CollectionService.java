package com.songpo.searched.service;

import com.songpo.searched.entity.SlMyCollection;
import com.songpo.searched.mapper.SlMyCollectionMapper;
import org.springframework.stereotype.Service;

@Service
public class CollectionService extends BaseService<SlMyCollection, String> {

    public CollectionService(SlMyCollectionMapper mapper) {
        super(mapper);
    }
}
