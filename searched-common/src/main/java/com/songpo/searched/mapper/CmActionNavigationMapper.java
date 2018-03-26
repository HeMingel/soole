package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Mapper
public interface CmActionNavigationMapper {

    /**
     * 根据配置项的KEY查询活动唯一标识符
     *
     * @param configKey 配置项
     * @return 动作导航列表
     */
    List<Map<String, Object>> selectByConfigKey(String configKey);
}
