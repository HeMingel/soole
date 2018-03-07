package com.songpo.service;

import com.songpo.entity.SlUser;
import com.songpo.mapper.SlUserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<SlUser, String> {

    public UserService(SlUserMapper mapper) {
        super(mapper);
    }

}
