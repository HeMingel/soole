package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmUserMapper {

    /**
     * 获取当前最大userName
     *
     * @return
     */
    Integer selectMaxUserName();

    /**
     * 需要插入的用户中心的数据列表
     * @return
     */
   List<Map<String,Object>> listForUserCenter();
}
