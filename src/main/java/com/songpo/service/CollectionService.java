package com.songpo.service;

import com.songpo.entity.SlMyCollection;
import com.songpo.entity.SlUser;
import com.songpo.mapper.SlMyCollectionMapper;
import com.songpo.mapper.SlUserMapper;
import org.springframework.stereotype.Service;

@Service
public class CollectionService extends BaseService<SlMyCollection, String> {

    public CollectionService(SlMyCollectionMapper mapper) {
        super(mapper);
    }
}
