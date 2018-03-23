package com.songpo.searched.service;


import com.songpo.searched.entity.SlSalesMode;
import com.songpo.searched.mapper.SlSalesModeMapper;
import org.springframework.stereotype.Service;

@Service
public class SalesModeService extends BaseService<SlSalesMode, String> {

    public SalesModeService(SlSalesModeMapper mapper) {
        super(mapper);
    }
}
