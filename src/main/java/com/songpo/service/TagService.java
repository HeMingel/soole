package com.songpo.service;


import com.songpo.entity.SlTag;
import com.songpo.mapper.SlTagMapper;
import org.springframework.stereotype.Service;

@Service
public class TagService extends BaseService<SlTag, String> {

    public TagService(SlTagMapper mapper) {
        super(mapper);
    }
}

