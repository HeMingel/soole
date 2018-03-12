package com.songpo.searched.mapper;

import com.songpo.searched.domain.ProductDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductMapper {

    List<ProductDto> findGoods(String name);

    List<ProductDto> findRecommendProduct();

    List<ProductDto> findGoodsByCategory(String goodsType);

    List<ProductDto> screenGoods(@Param("goodsType") String goodsType, @Param("screenGoods") Integer screenGoods);

    ProductDto goodsBaseDetail(String goodsId);


}


