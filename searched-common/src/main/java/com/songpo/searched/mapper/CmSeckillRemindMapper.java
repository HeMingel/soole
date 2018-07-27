package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlSeckillRemind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CmSeckillRemindMapper {
    //查询提醒列表
    List<SlSeckillRemind> listRemind (@Param("remindTime") String remindTime);

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
