package com.songpo.searched.service;


import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlSlbType;
import com.songpo.searched.mapper.SlProductMapper;
import com.songpo.searched.mapper.SlSlbTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SlSlbTypeService extends BaseService<SlSlbType, String> {


    @Autowired
    private SlSlbTypeMapper SlSlbTypeMapper;

    public SlSlbTypeService(SlSlbTypeMapper mapper) {
        super(mapper);
    }


}
