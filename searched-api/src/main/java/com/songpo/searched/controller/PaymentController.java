package com.songpo.searched.controller;

import com.songpo.searched.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘松坡
 */
@Api(description = "用户端管理")
@RestController
@RequestMapping("/api/common/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 微信支付支付通知处理接口
     *
     * @param request 请求参数
     * @return 支付通知处理结果
     */
    @PostMapping("wxpay-notify")
    public String wxPayNotify(HttpServletRequest request) {
        return this.paymentService.wxPayNotify(request);
    }

    /**
     * 支付宝支付通知处理接口
     *
     * @param request 请求参数
     * @return 支付通知处理结果
     */
    @PostMapping("alipay-notify")
    public String aliPayNotify(HttpServletRequest request) {
        return this.paymentService.wxPayNotify(request);
    }
}
