package com.songpo.searched.mapper;

import com.songpo.searched.domain.ProductCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductTypeMapper {

    List<ProductCategoryDto> findCategoryByParentId(String id);
}
