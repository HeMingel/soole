package com.songpo.searched.service;



import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.SlProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class ProductService extends BaseService<SlProduct, String> {


    @Autowired
    private SlProductMapper slProductMapper;

    public ProductService(SlProductMapper mapper) {
        super(mapper);
    }


}
