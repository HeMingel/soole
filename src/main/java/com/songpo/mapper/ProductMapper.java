package com.songpo.mapper;

import com.songpo.dto.ProductDto;

import java.util.List;


public interface ProductMapper {

     List<ProductDto> findGoods(String name);

     List<ProductDto> findRecommendProduct();

     List<ProductDto> findGoodsByCategory(String goodsType);
}


