package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrictArea;
import com.songpo.searched.mapper.SlDistrictAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictAreaService extends BaseService<SlDistrictArea, String> {

    public DistrictAreaService(SlDistrictAreaMapper mapper) {
        super(mapper);
    }
}
