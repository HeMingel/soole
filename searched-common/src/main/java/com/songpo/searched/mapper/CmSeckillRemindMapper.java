package com.songpo.searched.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * @author yinsl
 */
@Mapper
public interface CmSeckillRemindMapper {

    /**
     *  添加限时抢购提醒
     *  @param userId      用户ID
     *  @param productId   产品ID
     *  @param remindTime  提醒时间
     *
     */
    void insertSeckillRemind(@Param("userId") String userId,
                             @Param("productId") String productId,
                             @Param("remindTime") Date remindTime);

}
