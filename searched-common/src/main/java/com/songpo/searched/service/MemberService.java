package com.songpo.searched.service;

import com.songpo.searched.entity.SlMember;
import com.songpo.searched.mapper.SlMemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends BaseService<SlMember, String> {

    public MemberService(SlMemberMapper mapper) {
        super(mapper);
    }

}
