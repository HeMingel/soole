package com.songpo.searched.service;

import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.mapper.SlActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class ActivityService {

    @Autowired
    private SlActivityMapper mapper;

    public int save(SlActivity activity) {
        return this.mapper.insertSelective(activity);
    }
}
