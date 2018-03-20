package com.songpo.searched.controller;



import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.service.ProductTypeService;
import com.songpo.searched.validator.ProductTypeValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品类型管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/product-type")
public class ProductTypeController extends BaseController<SlProductType, String> {

    private Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    @Autowired
    private ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeValidator(service);
    }

}
