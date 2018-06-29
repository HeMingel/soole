package com.songpo.searched.service;

import com.songpo.searched.entity.SlOrderExtend;
import com.songpo.searched.mapper.SlOrderExtendMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderExtendService extends BaseService<SlOrderExtend, String> {
    public OrderExtendService(SlOrderExtendMapper mapper) {
        super(mapper);
    }
}
