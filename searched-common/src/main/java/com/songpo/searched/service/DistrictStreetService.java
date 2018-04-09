package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrictStreet;
import com.songpo.searched.mapper.SlDistrictStreetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictStreetService extends BaseService<SlDistrictStreet, String> {

    public DistrictStreetService(SlDistrictStreetMapper mapper) {
        super(mapper);
    }
}
