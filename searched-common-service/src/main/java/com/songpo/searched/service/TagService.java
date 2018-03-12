package com.songpo.searched.service;


import com.songpo.searched.entity.SlTag;
import com.songpo.searched.mapper.SlTagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagService extends BaseService<SlTag, String> {

    public TagService(SlTagMapper mapper) {
        super(mapper);
    }
}

