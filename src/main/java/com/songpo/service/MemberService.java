package com.songpo.service;

import com.songpo.entity.SlMember;
import com.songpo.mapper.SlMemberMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends BaseService<SlMember, String> {
    public MemberService(SlMemberMapper mapper) {
        super(mapper);
    }
}
