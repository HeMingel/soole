package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmOrderMapper {

    List<Map<String,Object>> findList(@Param("userId") String userId);

    String findUserAvatar(@Param("orderId") Object orderId);
}
