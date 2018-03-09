package com.songpo.mapper;

import com.songpo.dto.ProductCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
public interface ProductTypeMapper  {

     List<ProductCategoryDto> findCategoryByParentId(String id);
}
