package com.songpo.controller;

import com.songpo.entity.SlProductComment;
import com.songpo.entity.SlProductCommentImage;
import com.songpo.service.ProductCommentImageService;
import com.songpo.service.ProductCommentService;
import com.songpo.validator.ProductCommentImageValidator;
import com.songpo.validator.ProductCommentValidator;
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