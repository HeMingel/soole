package com.songpo.searched.service;

import com.songpo.searched.mapper.CmProductTypeMapper;
import com.songpo.searched.mapper.SlProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Service
public class CmProductTypeService {

    @Autowired
    private CmProductTypeMapper mapper;
    @Autowired
    private SlProductTypeMapper slProductTypeMapper;

    /**
     * 查询商品分类
     *
     * @return 商品分类集合
     */
    public List<Map<String, Object>> findAll(String parentId) {
        return this.mapper.selectAll(parentId);
    }

}
