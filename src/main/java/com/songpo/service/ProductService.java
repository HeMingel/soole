package com.songpo.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.domain.ProductDto;
import com.songpo.entity.SlProduct;
import com.songpo.mapper.ProductMapper;
import com.songpo.mapper.SlProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductService extends BaseService<SlProduct,String>{


    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SlProductMapper slProductMapper;


    public ProductService(SlProductMapper mapper) {
        super(mapper);
    }

    /**
     * 根据商品名称
     * @param name
     * @param page
     * @param size
     * @return
     */
    public BusinessMessage findGoods(String name, Integer page, Integer size){
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
        try{
            List<ProductDto> productDtos = this.productMapper.findGoods(name);
            if(productDtos.size() >0){
                businessMessage.setSuccess(true);
                businessMessage.setData(new PageInfo<>(productDtos));
                businessMessage.setMsg("查询成功");
            }else{
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        }catch (Exception e){
                businessMessage.setMsg("查询失败");
                log.error("查询商品失败",e);
        }

        return businessMessage;
    }

    /**
     * 首页推荐商品
     * 规则(按照时间排序 最新的6个)
     * @return
     */
    public BusinessMessage recommendProduct() {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try{
            List<ProductDto> productDtos = this.productMapper.findRecommendProduct();
            if(productDtos.size() > 0){
                 businessMessage.setMsg("查询成功");
                 businessMessage.setSuccess(true);
                 businessMessage.setData(productDtos);
            }else{
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        }catch (Exception e){
            businessMessage.setMsg("查询失败");
            log.error("查询商品失败",e);
        }

        return businessMessage;
    }

    public BusinessMessage findGoodsByCategory(String goodsType){
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try{
            List<ProductDto> productDtos = this.productMapper.findGoodsByCategory(goodsType);
            if(productDtos.size() > 0){
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
                businessMessage.setData(productDtos);
            }else{
                businessMessage.setMsg("查询无数据!");
                businessMessage.setSuccess(true);
            }

        }catch (Exception e){
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常",e);
        }
        return businessMessage;
    }

}
