package com.songpo.searched.controller;


import com.songpo.searched.entity.SlSalesMode;
import com.songpo.searched.service.SalesModeService;
import com.songpo.searched.validator.SalesModeValidator;
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
@RequestMapping("/api/base/v1/sales_mode")
public class SalesModeController extends BaseController<SlSalesMode, String> {

    private Logger logger = LoggerFactory.getLogger(SalesModeController.class);

    public SalesModeController(SalesModeService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new SalesModeValidator(service);
    }

}
