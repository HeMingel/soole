package com.songpo.searched.service;



import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.mapper.SlProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ProductTypeService extends BaseService<SlProductType, String> {

    @Autowired
    private SlProductTypeMapper slProductTypeMapper;


    public ProductTypeService(SlProductTypeMapper mapper) {
        super(mapper);
    }



}
