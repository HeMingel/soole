package com.songpo.searched.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.ProductDto;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.CmProductMapper;
import com.songpo.searched.mapper.SlProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义商品服务类
 * <p>
 * 主要提供处理跟商品相关的服务
 */
@Slf4j
@Service
public class CmProductService {

    @Autowired
    private CmProductMapper mapper;

    @Autowired
    private SlProductMapper slProductMapper;

    /**
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param actionId 活动唯一标识符
     * @param pageNum 页码
     * @param pageSize 容量
     * @return 商品分页列表
     */
    public PageInfo<SlProduct> selectByActionId(String actionId, Integer pageNum, Integer pageSize) {
        log.debug("根据活动唯一标识符查询商品列表，活动唯一标识符：{}，页码：{}，容量：{}", actionId, pageNum, pageSize);
        if (null == pageNum || pageNum <= 1) {
            pageNum = 1;
        }

        if (null == pageSize || pageSize <= 1) {
            pageSize = 10;
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<SlProduct> list = this.mapper.selectByActionId(actionId);

        return new PageInfo<>(list);
    }

    /**
     * 根据商品名称
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    public BusinessMessage findGoods(String name, Integer page, Integer size) {
        log.debug("查询 商品名称名称{}",name,"页数{}",page,"条数{}",size);
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
        try {
            List<ProductDto> productDtos = this.mapper.findGoods(name);
            if (productDtos.size() > 0) {
                businessMessage.setSuccess(true);
                businessMessage.setData(new PageInfo<>(productDtos));
                businessMessage.setMsg("查询成功");
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询失败");
            log.error("查询商品失败", e);
        }
        return businessMessage;
    }

    /**
     * 首页推荐商品
     * 规则(按照时间排序 最新的6个)
     *
     * @return
     */
    public BusinessMessage recommendProduct() {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            List<ProductDto> productDtos = this.mapper.findRecommendProduct();
            if (productDtos.size() > 0) {
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
                businessMessage.setData(productDtos);
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询失败");
            log.error("查询商品失败", e);
        }

        return businessMessage;
    }

    /**
     * 根据分类查询商品  +  商品筛选
     *
     * @param goodsType  商品分类ID
     * @param screenType 筛选类型
     * @param page
     * @param size
     * @return
     */
    public BusinessMessage screenGoods(String goodsType, Integer screenType, Integer page, Integer size) {
        log.debug("查询 商品分类Id{}",goodsType,"筛选条件{}",screenType,"页数{}",page,"条数{}",size);

        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            if (goodsType != null) {
                List<ProductDto> productDtos = this.mapper.screenGoods(goodsType, screenType);
                if (productDtos.size() > 0) {
                    businessMessage.setMsg("查询成功");
                    businessMessage.setSuccess(true);
                    businessMessage.setData(new PageInfo<>(productDtos));
                } else {
                    businessMessage.setMsg("查询无数据!");
                    businessMessage.setSuccess(true);
                }
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }

    /**
     * 根据商品Id 查询商品详情
     *
     * @param goodsId
     * @return
     */
    public BusinessMessage goodsDetail(String goodsId) {
        log.debug("查询 商品Id{}",goodsId);
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            //商品基础信息
            ProductDto productDto = this.mapper.goodsBaseDetail(goodsId);
            if (productDto.getPrice() != null) {

            } else {
                businessMessage.setMsg("!");
                businessMessage.setSuccess(true);
            }

        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }
}
