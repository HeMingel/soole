package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecification;
import com.songpo.searched.mapper.SlSpecificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpecificationService extends BaseService<SlSpecification, String> {

    public SpecificationService(SlSpecificationMapper mapper) {
        super(mapper);
    }


}
