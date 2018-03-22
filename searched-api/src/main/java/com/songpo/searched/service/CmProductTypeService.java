package com.songpo.searched.service;

import com.songpo.searched.mapper.CmProductTypeMapper;
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

    /**
     * 查询商品分类
     *
     * @param parentId 上级分类唯一标识符
     * @return 商品分类集合
     */
    public List<Map<String, Object>> findAll(String parentId) {
        return this.mapper.selectAll(parentId);
    }

}
