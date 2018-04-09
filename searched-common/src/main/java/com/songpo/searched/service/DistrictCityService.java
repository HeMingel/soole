package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrictCity;
import com.songpo.searched.mapper.SlDistrictCityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictCityService extends BaseService<SlDistrictCity, String> {

    public DistrictCityService(SlDistrictCityMapper mapper) {
        super(mapper);
    }
}
