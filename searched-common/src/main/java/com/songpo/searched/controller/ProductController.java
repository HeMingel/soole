package com.songpo.searched.controller;

import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.service.ProductService;
import com.songpo.searched.validator.ProductValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/product")
public class ProductController extends BaseController<SlProduct, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlProduct.class);

    public ProductController(ProductService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ProductValidator(service);
    }




}
