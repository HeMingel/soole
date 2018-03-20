package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 预下单订单
     *
     * @param slOrder
     * @param orderDetail
     * @return
     */
    @ApiOperation(value = "添加预下单")
    @PostMapping("add")
    public BusinessMessage addOrder(SlOrder slOrder, List<SlOrderDetail> orderDetail) {
        return this.orderService.addOrder(slOrder, orderDetail);
    }
}
