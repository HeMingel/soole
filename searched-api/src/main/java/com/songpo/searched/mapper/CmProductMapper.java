package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 刘松坡
 */
@Mapper
public interface CmProductMapper {

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品集合
     */
    List<SlProduct> selectByActionId(String actionId);
}
