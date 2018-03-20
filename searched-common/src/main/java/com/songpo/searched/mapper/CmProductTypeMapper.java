package com.songpo.searched.mapper;

import com.songpo.searched.domain.ProductCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmProductTypeMapper {

    Map findCategoryByParentId(String id);
}
