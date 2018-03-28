package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Mapper
public interface CmProductTypeMapper {

    /**
     * 查询商品分类
     *
     * @return 商品分类集合
     */
    List<Map<String, Object>> selectAll();

    List<Map<String,Object>> selectTwoMenu(String parentId);

}
