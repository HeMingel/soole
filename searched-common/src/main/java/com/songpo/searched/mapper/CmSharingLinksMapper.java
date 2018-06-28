package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface  CmSharingLinksMapper {

    List<Map<String ,Object>>  listByUserId (@Param("userId") String userId);

    /**
     * 根查询商品列表
     *
     * @param salesModeId  销售模式唯一标识符
     * @param activityId 商品活动ID
     * @param goodsTypeId 商品分类ID
     * @param goodsTypeStatus 商品分类标识 一级二级
     * @param longitudeMin 最小经度
     * @param longitudeMax 最大经度
     * @param latitudeMin  最小维度
     * @param latitudeMax  最大维度
     * @param sortByPrice  按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByCount  按库存排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByAward  按佣金排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin     价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax     价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param sortBySale  根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param addressNow 智能排序 当前地址 缺一不可
     * @param longitudeNow 智能排序 当前经度 缺一不可
     * @param latitudeNow 智能排序 当前纬度 缺一不可
     * @param synthesize 综合排序 (销量+评论数量)
     * @return 商品集合
     */
    List<Map<String, Object>> selectBySharingLinks(
                                                @Param("salesModeId") String salesModeId,
                                                @Param("activityId") String activityId,
                                                @Param("goodsTypeId") String goodsTypeId,
                                                @Param("goodsTypeStatus") Integer goodsTypeStatus,
                                                @Param("longitudeMin") Double longitudeMin,
                                                @Param("longitudeMax") Double longitudeMax,
                                                @Param("latitudeMin") Double latitudeMin,
                                                @Param("latitudeMax") Double latitudeMax,
                                                @Param("sortByPrice") String sortByPrice,
                                                @Param("sortByCount") String sortByCount,
                                                @Param("sortByAward") String sortByAward,
                                                @Param("priceMin") Integer priceMin,
                                                @Param("priceMax") Integer priceMax,
                                                @Param("sortBySale") String sortBySale,
                                                @Param("addressNow") String addressNow,
                                                @Param("longitudeNow")Double longitudeNow,
                                                @Param("latitudeNow")Double latitudeNow,
                                                @Param("synthesize")String synthesize);

    /**
     *查询红包信息 根据红包结果状态
     * @param result
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectRedPacketByResult(@Param("result") String result,
                                                      @Param("userId") String userId);
    /**
     *获取红包数 已领红包金额
     * @param userId
     * @return
     */
    List selectRedByUserId(@Param("userId") String userId);
}
