package com.songpo.searched.controller;

import com.songpo.searched.entity.SlProductComment;
import com.songpo.searched.service.ProductCommentService;
import com.songpo.searched.validator.ProductCommentValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品评价管理")
@RestController
@RequestMapping("/api/base/v1/product-comment")
public class ProductCommentController extends BaseController<SlProductComment, String> {

    private Logger logger = LoggerFactory.getLogger(ProductCommentController.class);

    public ProductCommentController(ProductCommentService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductCommentValidator(service);
    }
}