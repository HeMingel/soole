package com.songpo.searched.controller;

import com.songpo.searched.service.PaymentService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/common/v1/notify")
public class NotifyController {

    public static final Logger log = LoggerFactory.getLogger(NotifyController.class);

    private final PaymentService paymentService;

    @Autowired
    public NotifyController(PaymentService paymentService) {
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
        log.debug("微信支付通知接口开始调用");
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
        return this.paymentService.aliPayNotify(request);
    }

    /**
     * 分享通知回调
     *
     * @param req 请求参数
     */
    @PostMapping("share-notify")
    public void shareNotify(HttpServletRequest req) {
        log.debug("接收分享回调");
        req.getParameterMap().forEach((k, v) -> {
            log.debug("参数Key: {}，参数值：{}", k, String.join(",", v));
        });
    }

}
