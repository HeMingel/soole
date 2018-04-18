package com.songpo.searched.controller;

import com.songpo.searched.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 刘松坡
 */
@Api(description = "用户端管理")
@RestController
@RequestMapping("/api/common/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("wx-notify")
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        return this.paymentService.wxNotify(request, response);
    }
}
