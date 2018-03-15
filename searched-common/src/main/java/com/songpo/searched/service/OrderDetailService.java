package com.songpo.searched.service;

import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.mapper.SlOrderDetailMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService extends BaseService<SlOrderDetail, String> {
    public OrderDetailService(SlOrderDetailMapper mapper) {
        super(mapper);
    }
}
