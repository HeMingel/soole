package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CmUserMapper {

    /**
     * 获取当前最大userName
     *
     * @return
     */
    Integer selectMaxUserName();
}
