package com.songpo.searched.controller;

import com.songpo.searched.entity.SlProductCommentImage;
import com.songpo.searched.service.ProductCommentImageService;
import com.songpo.searched.validator.ProductCommentImageValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品评价管理图片")
@RestController
@RequestMapping("/api/v1/product-comment-image")
public class ProductCommentImageController extends BaseController<SlProductCommentImage, String> {

    private Logger logger = LoggerFactory.getLogger(ProductCommentImageController.class);

    public ProductCommentImageController(ProductCommentImageService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductCommentImageValidator(service);
    }
}