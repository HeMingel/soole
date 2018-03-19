package com.songpo.searched.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.ProductDto;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.ProductMapper;
import com.songpo.searched.mapper.SlProductMapper;
import com.songpo.searched.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductService extends BaseService<SlProduct, String> {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SlProductMapper slProductMapper;

    public ProductService(SlProductMapper mapper) {
        super(mapper);
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
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
        try {
            List<ProductDto> productDtos = this.productMapper.findGoods(name);
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
            List<ProductDto> productDtos = this.productMapper.findRecommendProduct();
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
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            if (goodsType != null) {
                List<ProductDto> productDtos = this.productMapper.screenGoods(goodsType, screenType);
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

        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            //商品基础信息
            ProductDto productDto = this.productMapper.goodsBaseDetail(goodsId);
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
