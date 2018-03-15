package com.songpo.searched.controller;


import com.songpo.searched.entity.SlProductImage;
import com.songpo.searched.service.ProductImageService;
import com.songpo.searched.validator.ProductImageValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品图片")
@RestController
@RequestMapping("/api/v1/product-image")
public class ProductImageController extends BaseController<SlProductImage, String> {

    private Logger logger = LoggerFactory.getLogger(ProductImageController.class);

    public ProductImageController(ProductImageService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductImageValidator(service);
    }

}
