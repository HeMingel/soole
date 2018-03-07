package com.songpo.controller;


import com.songpo.entity.SlProductTypeTags;
import com.songpo.entity.SlShop;
import com.songpo.service.ProductTypeTagsService;
import com.songpo.service.ShopService;
import com.songpo.validator.ProductTypeTagsValidator;
import com.songpo.validator.ShopValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商店标签")
@RestController
@RequestMapping("/api/v1/productTypeTags")
public class ProductTypeTagsController extends BaseController<SlProductTypeTags, String>{

    private Logger logger = LoggerFactory.getLogger(ProductTypeTagsController.class);

    public ProductTypeTagsController(ProductTypeTagsService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeTagsValidator(service);
    }

}
