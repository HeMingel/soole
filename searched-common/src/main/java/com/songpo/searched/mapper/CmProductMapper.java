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
public interface CmProductMapper{

    /**
     * 根据销售模式查询商品列表
     *
     * @param name         商品名称
     * @param salesModeId  销售模式唯一标识符
     * @param longitudeMin 最小经度
     * @param longitudeMax 最大经度
     * @param latitudeMin  最小维度
     * @param latitudeMax  最大维度
     * @param sortByPrice  按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByRating 按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin     价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax     价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @return 商品集合
     */
    List<Map<String, Object>> selectBySalesMode(@Param("name") String name,
                                                @Param("salesModeId") String salesModeId,
                                                @Param("longitudeMin") Double longitudeMin,
                                                @Param("longitudeMax") Double longitudeMax,
                                                @Param("latitudeMin") Double latitudeMin,
                                                @Param("latitudeMax") Double latitudeMax,
                                                @Param("sortByPrice") String sortByPrice,
                                                @Param("sortByRating") String sortByRating,
                                                @Param("priceMin") Integer priceMin,
                                                @Param("priceMax") Integer priceMax);

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品集合
     */
    List<SlProduct> selectByAction(String actionId);

    /**
     * 查询推荐商品 最新商品前6个
     * @return 商品列表
     */
    List<Map<String,Object>> findRecommendProduct();
    /**
     * 分类查询商品 + 商品的筛选 + 根据名称查询
     * @param goodsType 商品分类
     * @param screenGoods 筛选条件
     * @param saleMode 销售模式
     * @param goodsName 商品名称
     * @return 商品列表
     */
    List<Map<String, Object>> screenGoods(@Param("goodsType") String goodsType, @Param("screenGoods") Integer screenGoods, @Param("saleMode") Integer saleMode, @Param("goodsName") String goodsName);
    /**
     * 查询商品基础信息
     * @param goodsId 商品ID
     * @return 商品product 表相关信息
     */
    Map<String,Object> goodsBaseInfo(String goodsId);
    /**
     * 查询上商品所有图片
     * @param goodsId 商品ID
     * @return 商品图片
     */
    List<Map<String, Object>> goodsImageUrl(String goodsId);
    /**
     * 查询商品规格详情
     * @param goodsId 商品Id
     * @return 商品规格详情
     */
    List<Map<String,Object>> goodsSpecificationDetail(String goodsId);
    /**
     * 查询商品规格
     * @param goodsId 商品ID
     * @return 商品规格
     */
    List<Map<String,Object>> goodsSpecification(String goodsId);
    /**
     * 查询商品库存
     * @param goodsId 商品Id
     * @return 商品库存
     */
    List<Map<String,Object>> goodsRepository(String goodsId);
    /**
     * 查询商品库存规格规格id名称
     * @param productDetailGroupSerialNumber 商品分组
     * @return 商品库存规格id名称
     */
    List<Map<String,Object>> goodsRepositorySpecification(String productDetailGroupSerialNumber);

    /**
     * 查询拼团商品列表
     *
     * @return 商品列表
     */
    List<Map<String, Object>> selectByTeamwork();

    /**
     * 查询预售商品列表
     *
     * @return 商品列表
     */
    List<Map<String, Object>> selectByPreSales();

    /**
     * 查询商品拼团订单
     *
     * @param goodsId 商品Id
     * @param saleModeType  商品销售模式分类
     *@param peopleNum 拼团人数
     * @return 该商品拼团信息
     */
    List<Map<String,Object>> findGroupOrder(@Param("goodsId")String goodsId,@Param("saleModeType")Integer saleModeType,@Param("peopleNum")Integer peopleNum);

    /**
     * 未完成拼团
     * @param orderNum 订单编号
     * @return 第一个发起人信息
     */
    Map<String,Object> findGroupPeople(String orderNum);

    /**
     * 查询热门商品推荐
     * @param goodsId 商品ID
     * @param saleModeId 销售模式id
     * @return
     */
    List<Map<String, Object>> hotGoods(@Param("goodsId")String goodsId,@Param("saleModeId")String saleModeId);
}
