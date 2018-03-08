package com.songpo.service;

import com.songpo.entity.SlUser;
import com.songpo.mapper.SlUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends BaseService<SlUser, String> {

    public UserService(SlUserMapper mapper) {
        super(mapper);
    }

}
