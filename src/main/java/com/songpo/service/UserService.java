package com.songpo.service;

import com.songpo.cache.UserCache;
import com.songpo.entity.SlUser;
import com.songpo.mapper.SlUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService extends BaseService<SlUser, String> {

    @Autowired
    private UserCache cache;

    public UserService(SlUserMapper mapper) {
        super(mapper);
    }
}
