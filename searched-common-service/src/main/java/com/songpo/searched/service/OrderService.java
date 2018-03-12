package com.songpo.searched.service;

import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.mapper.SlOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService<SlOrder, String> {
    public OrderService(SlOrderMapper mapper) {
        super(mapper);
    }
}
