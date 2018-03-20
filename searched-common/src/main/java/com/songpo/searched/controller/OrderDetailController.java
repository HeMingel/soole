package com.songpo.searched.controller;

import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.service.OrderDetailService;
import com.songpo.searched.validator.OrderDetailValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "用户管理")
@RestController
@RequestMapping("/api/base/v1/order-detail")
public class OrderDetailController extends BaseController<SlOrderDetail, String> {
    public OrderDetailController(OrderDetailService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new OrderDetailValidator(service);
    }
}
