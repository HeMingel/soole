package com.songpo.controller;


import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.entity.SlProductType;
import com.songpo.entity.SlShop;
import com.songpo.service.ProductTypeService;
import com.songpo.service.ShopService;
import com.songpo.validator.ProductTypeValidator;
import com.songpo.validator.ShopValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Y.H
 */
@Api(description = "商品类型管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/product-type")
public class ProductTypeController extends BaseController<SlProductType, String>{

    private Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    @Autowired
    private ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeValidator(service);
    }

    @RequestMapping(value = "/find-category",method = RequestMethod.POST)
    public BusinessMessage findCategory() {
        return this.productTypeService.findCategory();
    }

    @RequestMapping(value = "/find-category-by-parentId",method = RequestMethod.POST)
    public BusinessMessage findCategoryByParentId(String parentId) {
        if(parentId == null){
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setSuccess(false);
            businessMessage.setMsg("父ID为空!");
            return businessMessage;
        }
        return this.productTypeService.findCategoryByParentId(parentId);
    }
}
