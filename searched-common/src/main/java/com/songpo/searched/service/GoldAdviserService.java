package com.songpo.searched.service;

import com.songpo.searched.entity.SlGoldAdviser;
import com.songpo.searched.mapper.SlGoldAdviserMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class GoldAdviserService extends BaseService<SlGoldAdviser, String> {

    public GoldAdviserService(SlGoldAdviserMapper mapper) {
        super(mapper);
    }
}
