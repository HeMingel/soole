package com.songpo.searched.service;

import com.songpo.searched.entity.SlUserSlb;
import com.songpo.searched.mapper.SlUserSlbMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSlbService extends BaseService<SlUserSlb, String> {

    public UserSlbService(SlUserSlbMapper mapper) {
        super(mapper);
    }
}
