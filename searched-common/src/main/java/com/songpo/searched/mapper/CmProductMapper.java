package com.songpo.searched.mapper;

import com.songpo.searched.domain.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Mapper
public interface CmProductMapper {

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品集合
     */
    List<Map<String, Object>> selectAll(@Param("name") String name, @Param("actionId") String actionId);

    /**
     * 查询拼团商品
     *
     * @param actionId 拼团活动唯一标识符
     * @return
     */
    List<Map<String, Object>> selectTeamworkProduct(String actionId);

    //List<ProductDto> findGoods(String name);

    List<ProductDto> findRecommendProduct();

    List<ProductDto> findGoodsByCategory(String goodsType);
    //根据分类查询商品 + 商品的筛选 + 根据名称查询
    List<ProductDto> screenGoods(@Param("goodsType") String goodsType, @Param("screenGoods") Integer screenGoods,@Param("goodsName")String goodsName);

    Map goodsBaseDetail(String goodsId);

    Map goodsImageUrl(String goodsId);

}
