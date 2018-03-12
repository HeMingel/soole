package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlUserMapper;
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
