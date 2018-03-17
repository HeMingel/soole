package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.ProductCategoryDto;
import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.mapper.ProductTypeMapper;
import com.songpo.searched.mapper.SlProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProductTypeService extends BaseService<SlProductType, String> {

    @Autowired
    private SlProductTypeMapper slProductTypeMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    public ProductTypeService(SlProductTypeMapper mapper) {
        super(mapper);
    }

    /**
     * 查询商品一级分类
     *
     * @return
     */
    public BusinessMessage findCategory() {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            Example example = new Example(SlProductType.class);
            example.createCriteria().andIsNull("parentId");
            List<SlProductType> slProductTypeList = this.slProductTypeMapper.selectByExample(example);
            if (slProductTypeList.size() > 0) {
                businessMessage.setData(slProductTypeList);
                businessMessage.setSuccess(true);
            } else {
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询无数据!");
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询分类失败");
        }
        return businessMessage;
    }

    /**
     * 根据商品parentId 查询商品商品二级分类
     *
     * @return
     */
    public BusinessMessage findCategoryByParentId(String id) {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            List<ProductCategoryDto> productCategoryDtos = this.productTypeMapper.findCategoryByParentId(id);
            if (productCategoryDtos.size() > 0) {
                businessMessage.setData(productCategoryDtos);
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");
            } else {
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询无数据");
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询失败");
        }
        return businessMessage;
    }

}
