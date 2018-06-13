package com.songpo.searched.service;

import com.songpo.searched.entity.SlProductNoMail;
import com.songpo.searched.mapper.SlProductNoMailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductNoMailService extends BaseService<SlProductNoMail, String>{

    @Autowired
    private  SlProductNoMailMapper slProductNoMailMapper;

    public ProductNoMailService(SlProductNoMailMapper mapper) {
        super(mapper);
    }
}
