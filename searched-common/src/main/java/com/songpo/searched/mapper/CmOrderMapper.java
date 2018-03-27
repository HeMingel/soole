package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmOrderMapper {

    List<Map<String, Object>> findList(@Param("userId") String userId);

    List<String> findUserAvatar(@Param("serialNumber") Object serialNumber);

    Map<String, Object> selectMyOrderInfo(@Param("userId") String userId, @Param("orderId") String orderId);

    String findGroup(@Param("productId") String productId);
}
