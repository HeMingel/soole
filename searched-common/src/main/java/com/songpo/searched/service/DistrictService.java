package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrict;
import com.songpo.searched.mapper.SlDistrictMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictService extends BaseService<SlDistrict, String> {

    public DistrictService(SlDistrictMapper mapper) {
        super(mapper);
    }
}
