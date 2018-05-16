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
     * @param activityId 商品活动ID
     * @param goodsTypeId 商品分类ID
     * @param goodsTypeStatus 商品分类标识 一级二级
     * @param longitudeMin 最小经度
     * @param longitudeMax 最大经度
     * @param latitudeMin  最小维度
     * @param latitudeMax  最大维度
     * @param sortByPrice  按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByRating 按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin     价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax     价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param sortBySale  根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param addressNow 智能排序 当前地址 缺一不可
     * @param longitudeNow 智能排序 当前经度 缺一不可
     * @param latitudeNow 智能排序 当前纬度 缺一不可
     * @param synthesize 综合排序 (销量+评论数量)
     * @return 商品集合
     */
    List<Map<String, Object>> selectBySalesMode(@Param("name") String name,
                                                @Param("salesModeId") String salesModeId,
                                                @Param("activityId") String activityId,
                                                @Param("goodsTypeId") String goodsTypeId,
                                                @Param("goodsTypeStatus") Integer goodsTypeStatus,
                                                @Param("longitudeMin") Double longitudeMin,
                                                @Param("longitudeMax") Double longitudeMax,
                                                @Param("latitudeMin") Double latitudeMin,
                                                @Param("latitudeMax") Double latitudeMax,
                                                @Param("sortByPrice") String sortByPrice,
                                                @Param("sortByRating") String sortByRating,
                                                @Param("priceMin") Integer priceMin,
                                                @Param("priceMax") Integer priceMax,
                                                @Param("sortBySale") String sortBySale,
                                                @Param("addressNow") String addressNow,
                                                @Param("longitudeNow")Double longitudeNow,
                                                @Param("latitudeNow")Double latitudeNow,
                                                @Param("synthesize")String synthesize);

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品集合
     */
    List<SlProduct> selectByAction(String actionId);

    /**
     * 分类查询商品 + 商品的筛选 + 根据名称查询
     * @param goodsTypeId 商品分类
     * @param screenGoods 筛选条件
     * @param saleMode 销售模式
     * @param goodsName 商品名称
     * @return 商品列表 goodsType, screenType, saleMode, name
     */
    List<Map<String, Object>> screenGoods(@Param("goodsTypeId") String goodsTypeId, @Param("screenGoods") Integer screenGoods, @Param("saleMode") Integer saleMode, @Param("goodsName") String goodsName,@Param("activityId")String activityId);

    /**
     * 查询拼团商品的头像
     * @param goodsId 商品id
     * @return 头像列表
     */
    List<Map<String, Object>> selectGroupAvatar(String goodsId);

    /**
     * 查询客服
     * @param shopId 商店ID
     * @return 客服信息
     */
    Map<String,String> selectCustomerService(String shopId);
    /**
     * 根据名称查询商品
     * @param goodsName 商品名称
     * @return 商品列表
     */
    List<Map<String,Object>> selectByName(String goodsName);

    /**
     * 查询商品基础信息
     * @param goodsId 商品ID
     * @param activityId 活动Id
     * @return 商品product 表相关信息
     */
    Map<String,Object> goodsBaseInfo(@Param("goodsId")String goodsId,@Param("activityId")String activityId);

    /**
     * 查询品活动中间表
     * @param goodsId 商品ID
     * @param activityId 活动id
     * @return
     */
    List<Map<String,Object>> goodsActivity(@Param("goodsId")String goodsId,@Param("activityId")String activityId);
    /**
     * 查询上商品所有图片
     * @param goodsId 商品ID
     * @return 商品图片
     */
    List<Map<String, Object>> goodsImageUrl(String goodsId);

    /**
     * 查询商品活动
     * @param goodsId 商品Id
     * @param activityId 活动Id
     * @return 商品活动列表
     */
    List<Map<String, Object>> goodsActivityList(@Param("goodsId")String goodsId,@Param("activityId")String activityId);

    /**
     * 查询商品库存
     * @param repositoryId 商品库存Id
     * @return 商品库存
     */
    Map<String,Object> goodsRepository(@Param("repositoryId")String repositoryId,@Param("activityId") String activityId);
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
     * @param userId 用户Id 这里指拼团人的Id
     * @return 第一个发起人信息
     */
    Map<String,Object> findGroupPeople(String userId);

    /**
     * 查询拼团商品订单
     * @return 拼团商品订单
     */

    List<Map<String,Object>> selectGroupOrder(@Param("activityId")String activityId,@Param("goodsId")String goodsId);

    /**
     * 查询该活动商品已经购买成功的订单
     * @param activityId
     * @param goodsId
     * @return
     */
    List<Map<String,Object>> alreadyOrder(@Param("activityId")String activityId,@Param("goodsId")String goodsId);

    /**
     * 后台推荐产品
     * @param goodsId 商品Id
     *                @param activityId 活动id
     * @return 后台推荐商品列表
     */
    List<Map<String,Object>> backStageGoods(@Param("goodsId")String goodsId,@Param("activityId")String activityId);

    /**
     * 系统推荐商品商品分类
     * @param goodsId 商品Id
     * @return 该商品分类
     */
    Map<String ,Object> systemGoodsType(String goodsId);
    /**
     * 系统推荐上品
     * @param goodsType 商品type
     * @param activityId 活动id
     * @return 该商品分类
     */
    List<Map<String,Object>> systemGoods(@Param("goodsType")String goodsType,@Param("activityId")String activityId);

    /**
     * 商铺商品查询筛选
     * @param shopId 店铺id
     * @param goodsName 商品名称
     * @param sortBySale 根据销量排序
     * @param sortByPrice 根据价格排序
     * @return 商品列表
     */

    List<Map<String,Object>> selectShopGoods(@Param("shopId")String shopId,
                                             @Param("goodsName")String goodsName,
                                             @Param("sortBySale")String sortBySale,
                                             @Param("sortByPrice")String sortByPrice);

}
