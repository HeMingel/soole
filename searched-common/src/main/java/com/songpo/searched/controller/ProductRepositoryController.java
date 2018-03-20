package com.songpo.searched.controller;


import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.service.ProductRepositoryService;
import com.songpo.searched.validator.ProductRepositoryValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商店库存管理")
@RestController
@CrossOrigin
@RequestMapping("/api/base/v1/repository")
public class ProductRepositoryController extends BaseController<SlProductRepository, String> {

    private Logger logger = LoggerFactory.getLogger(ProductRepositoryController.class);

    public ProductRepositoryController(ProductRepositoryService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductRepositoryValidator(service);
    }

}
