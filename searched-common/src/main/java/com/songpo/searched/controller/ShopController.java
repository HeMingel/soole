package com.songpo.searched.controller;


import com.songpo.searched.entity.SlShop;
import com.songpo.searched.service.ShopService;
import com.songpo.searched.validator.ShopValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商店管理")
@RestController
@CrossOrigin
@RequestMapping("/api/base/v1/shop")
public class ShopController extends BaseController<SlShop, String> {

    private Logger logger = LoggerFactory.getLogger(ShopController.class);

    public ShopController(ShopService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ShopValidator(service);
    }

}
