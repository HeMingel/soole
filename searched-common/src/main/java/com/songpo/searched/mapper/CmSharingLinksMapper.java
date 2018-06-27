package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface  CmSharingLinksMapper {

    List<Map<String ,Object>>  listByUserId (@Param("userId") String userId);
}
