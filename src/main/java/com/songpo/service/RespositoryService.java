package com.songpo.service;

import com.songpo.entity.SlRepository;
import com.songpo.mapper.SlRepositoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RespositoryService extends BaseService<SlRepository, String> {
    public RespositoryService(SlRepositoryMapper mapper) {
        super(mapper);
    }
}
