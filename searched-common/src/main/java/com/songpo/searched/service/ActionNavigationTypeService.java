package com.songpo.searched.service;

import com.songpo.searched.entity.SlActionNavigationType;
import com.songpo.searched.mapper.SlActionNavigationTypeMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class ActionNavigationTypeService extends BaseService<SlActionNavigationType, String> {

    public ActionNavigationTypeService(SlActionNavigationTypeMapper mapper) {
        super(mapper);
    }
}
