package com.songpo.service;

import com.songpo.entity.SlRepository;
import com.songpo.mapper.SlRepositoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService extends BaseService<SlRepository, String> {
    public RepositoryService(SlRepositoryMapper mapper) {
        super(mapper);
    }
}
