package com.songpo.searched.service;

import com.songpo.searched.entity.SlSpecificationDetail;
import com.songpo.searched.mapper.SlSpecificationDetailMapper;
import org.springframework.stereotype.Service;

@Service
public class SpecificationDetailService extends BaseService<SlSpecificationDetail, String> {

    public SpecificationDetailService(SlSpecificationDetailMapper mapper) {
        super(mapper);
    }


}
