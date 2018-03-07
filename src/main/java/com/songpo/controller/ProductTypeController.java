package com.songpo.controller;


import com.songpo.entity.SlProductType;
import com.songpo.entity.SlShop;
import com.songpo.service.ProductTypeService;
import com.songpo.service.ShopService;
import com.songpo.validator.ProductTypeValidator;
import com.songpo.validator.ShopValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品类型管理")
@RestController
@RequestMapping("/api/v1/productType")
public class ProductTypeController extends BaseController<SlProductType, String>{

    private Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    public ProductTypeController(ProductTypeService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeValidator(service);
    }

}
