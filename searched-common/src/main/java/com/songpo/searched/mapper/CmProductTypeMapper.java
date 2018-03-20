package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmProductTypeMapper {

    List<Map<String, Object>> findCategoryByParentId(String id);
}
