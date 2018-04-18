package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecification;
import com.songpo.searched.mapper.SlSpecificationMapper;
import org.springframework.stereotype.Service;

@Service
public class SpecificationService extends BaseService<SlSpecification, String> {

    public SpecificationService(SlSpecificationMapper mapper) {
        super(mapper);
    }


}
