package com.songpo.searched.service;

import com.songpo.searched.entity.SlDistrictVillage;
import com.songpo.searched.mapper.SlDistrictVillageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictVillageService extends BaseService<SlDistrictVillage, String> {

    public DistrictVillageService(SlDistrictVillageMapper mapper) {
        super(mapper);
    }
}
