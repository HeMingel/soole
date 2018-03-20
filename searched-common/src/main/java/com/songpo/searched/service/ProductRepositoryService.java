package com.songpo.searched.service;

import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.mapper.SlProductRepositoryMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductRepositoryService extends BaseService<SlProductRepository, String> {
    public ProductRepositoryService(SlProductRepositoryMapper mapper) {
        super(mapper);
    }
}
