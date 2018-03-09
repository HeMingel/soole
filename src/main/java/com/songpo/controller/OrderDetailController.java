package com.songpo.controller;

import com.songpo.entity.SlOrderDetail;
import com.songpo.service.OrderDetailService;
import com.songpo.validator.OrderDetailValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "用户管理")
@RestController
@RequestMapping("/api/v1/order-detail")
public class OrderDetailController extends BaseController<SlOrderDetail, String> {
    public OrderDetailController(OrderDetailService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new OrderDetailValidator(service);
    }
}
