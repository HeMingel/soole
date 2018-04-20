package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecificationDetailRepository;
import com.songpo.searched.mapper.SlSpecificationDetailRepositoryMapper;
import org.springframework.stereotype.Service;

@Service
public class SpecificationDetailRepositoryService extends BaseService<SlSpecificationDetailRepository, String> {

    public SpecificationDetailRepositoryService(SlSpecificationDetailRepositoryMapper mapper) {
        super(mapper);
    }


}
