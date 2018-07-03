package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmUserSlbMapper {

    /**
     *@param userId    用户OD
     *@param slbType  搜了贝类型
     *@return 查询搜了贝详情
     */
    List<Map<String, Object>> selectUserSlbDetail(@Param("userId") String userId,
                                              @Param("slbType") Integer slbType);

    /**
     *@param userId    用户OD
     *@return 查询总搜了贝
     */
    List<Map<String, Object>> selectSumSlb(@Param("userId") String userId);
}
