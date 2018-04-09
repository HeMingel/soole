package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrictProvince;
import com.songpo.searched.mapper.SlDistrictProvinceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictProvinceService extends BaseService<SlDistrictProvince, String> {

    public DistrictProvinceService(SlDistrictProvinceMapper mapper) {
        super(mapper);
    }
}
