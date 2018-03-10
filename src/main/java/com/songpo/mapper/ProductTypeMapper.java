package com.songpo.mapper;

import com.songpo.domain.ProductCategoryDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface ProductTypeMapper  {

     List<ProductCategoryDto> findCategoryByParentId(String id);
}
