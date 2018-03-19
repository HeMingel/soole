package com.songpo.searched.service;

import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.CmProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义商品服务类
 * <p>
 * 主要提供处理跟商品相关的服务
 */
@Service
public class CmProductService {

    @Autowired
    private CmProductMapper mapper;

    /**
     * 根据活动唯一标识符查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @return 商品列表
     */
    public List<SlProduct> selectByActionId(String actionId) {
        return this.mapper.selectByActionId(actionId);
    }
}
