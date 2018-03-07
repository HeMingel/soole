package com.songpo.service;

import com.songpo.entity.SlOrderDetail;
import com.songpo.entity.SlUser;
import com.songpo.mapper.SlOrderDetailMapper;
import com.songpo.mapper.SlUserMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService extends BaseService<SlOrderDetail, String> {
    public OrderDetailService(SlOrderDetailMapper mapper) {
        super(mapper);
    }
}
