package com.songpo.controller;

import com.songpo.entity.SlOrder;
import com.songpo.entity.SlUser;
import com.songpo.service.OrderService;
import com.songpo.validator.OrderValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/order")
public class OrderController extends BaseController<SlOrder, String> {
    public OrderController(OrderService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new OrderValidator(service);
        
    }
}
