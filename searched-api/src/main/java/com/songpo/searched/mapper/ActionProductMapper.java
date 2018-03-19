package com.songpo.searched.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 活动商品
 * 用来处理参与活动的商品，例如某个商品加入拼团、秒杀等活动
 *
 * @author 刘松坡
 */
public interface ActionProductMapper {

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识
     * @return
     */
    List<Map<String, Object>> findByActionId(@Param("action_id") String actionId);
}
