package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlSeckillRemind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CmSeckillRemindMapper {
    //查询提醒列表
    List<SlSeckillRemind> listRemind (@Param("remindTime") String remindTime);
}
