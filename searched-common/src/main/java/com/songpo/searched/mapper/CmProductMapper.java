package com.songpo.searched.mapper;

import com.songpo.searched.domain.CmProduct;
import com.songpo.searched.entity.SlProduct;
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
     * 根据销售模式查询商品列表
     *
     * @param name 商品名称
     * @param salesModeId 销售模式唯一标识符
     * @return 商品集合
     */
    List<Map<String, Object>> selectBySalesMode(@Param("name") String name, @Param("salesModeId") String salesModeId);

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品集合
     */
    List<SlProduct> selectByAction(String actionId);

    /**
     * 查询推荐商品 最新商品前6个
     * @return
     */
    List<CmProduct> findRecommendProduct();

    //根据分类查询商品 + 商品的筛选 + 根据名称查询
    List<Map<String,Object>> screenGoods(@Param("goodsType") String goodsType, @Param("screenGoods") Integer screenGoods, @Param("goodsName") String goodsName);

    //获取product表中相关信息
    Map goodsBaseInfo(String goodsId);
    //获取product_image 表中图片
    List<Map<String,Object>> goodsImageUrl(String goodsId);


}
