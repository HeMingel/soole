package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlActivityProduct;
import com.songpo.searched.entity.SlReturnsDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmOrderMapper {

    List<Map<String, Object>> findList(@Param("userId") String userId, @Param("status") Integer status);

    List<String> findUserAvatar(@Param("serialNumber") Object serialNumber);

    Map<String, Object> selectMyOrderInfo(@Param("userId") String userId, @Param("id") String id);

//    Map<String, Object> findActivityProduct(@Param("productId") String productId);

    Integer selectOrdersCount(@Param("productId") String productId, @Param("userId") String userId, @Param("activityProductId") String activityProductId);

//    List<String> findProductsRepositoryId(@Param("productId") String productId, @Param("activityId") String activityId);

    Integer groupOrdersByUser(@Param("serialNumber") String serialNumber);

    SlActivityProduct selectActivityProductByRepositoryId(@Param("repositoryId") String repositoryId, @Param("activityProductId") String activityProductId);

    Map<String,Object> selectShopUserName(String shopId);

    List<SlReturnsDetail> selectReturnsDetail(@Param("status")Integer status, @Param("userId")String userId);
}
