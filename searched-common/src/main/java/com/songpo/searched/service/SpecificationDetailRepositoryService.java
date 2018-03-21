package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecificationDetail;
import com.songpo.searched.entity.SlSpecificationDetailRepository;
import com.songpo.searched.mapper.SlSpecificationDetailMapper;
import com.songpo.searched.mapper.SlSpecificationDetailRepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpecificationDetailRepositoryService extends BaseService<SlSpecificationDetailRepository, String> {

    public SpecificationDetailRepositoryService(SlSpecificationDetailRepositoryMapper mapper) {
        super(mapper);
    }


}
