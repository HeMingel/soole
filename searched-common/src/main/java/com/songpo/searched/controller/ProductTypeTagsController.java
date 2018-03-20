package com.songpo.searched.controller;


import com.songpo.searched.entity.SlProductTypeTags;
import com.songpo.searched.service.ProductTypeTagsService;
import com.songpo.searched.validator.ProductTypeTagsValidator;
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
@RequestMapping("/api/base/v1/product-type-tags")
public class ProductTypeTagsController extends BaseController<SlProductTypeTags, String> {

    private Logger logger = LoggerFactory.getLogger(ProductTypeTagsController.class);

    public ProductTypeTagsController(ProductTypeTagsService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeTagsValidator(service);
    }

}
