package com.songpo.searched.service;

import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.mapper.SlActivityMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class ActivityService extends BaseService<SlActivity, String> {

    public ActivityService(SlActivityMapper mapper) {
        super(mapper);
    }
}
