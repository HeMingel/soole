package com.songpo.service;

import com.songpo.entity.SlOrder;
import com.songpo.mapper.SlOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService<SlOrder, String> {
    public OrderService(SlOrderMapper mapper) {
        super(mapper);
    }
}
