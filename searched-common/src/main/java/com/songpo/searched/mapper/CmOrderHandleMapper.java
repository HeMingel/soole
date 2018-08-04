package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface CmOrderHandleMapper {

    /**
     *  添加钱包异常订单
     *  @param orderId  订单ID
     *  @param userId   用户ID
     *  @param message  异常信息
     *
     */
    void insertOrderHandle(@Param("orderId") String orderId,
                          @Param("userId") String userId,
                          @Param("message") String  message);

}
