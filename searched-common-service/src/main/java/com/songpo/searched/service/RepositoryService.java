package com.songpo.searched.service;

import com.songpo.searched.entity.SlRepository;
import com.songpo.searched.mapper.SlRepositoryMapper;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService extends BaseService<SlRepository, String> {
    public RepositoryService(SlRepositoryMapper mapper) {
        super(mapper);
    }
}
