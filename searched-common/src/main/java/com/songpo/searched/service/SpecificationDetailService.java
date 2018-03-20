package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecification;
import com.songpo.searched.entity.SlSpecificationDetail;
import com.songpo.searched.mapper.SlSpecificationDetailMapper;
import com.songpo.searched.mapper.SlSpecificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpecificationDetailService extends BaseService<SlSpecificationDetail, String> {

    public SpecificationDetailService(SlSpecificationDetailMapper mapper) {
        super(mapper);
    }


}
