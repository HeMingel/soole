package com.songpo.searched.controller;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.util.OrderNumGeneration;
import com.songpo.searched.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(description = "第三方支付")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/tppay")
public class ThirdPartyPayController {
    public static final Logger log = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AliPayService aliPayService;

    /**
     * 微信退款
     * @param outTradeNo 商户订单号
     * @param totalFeeStr 订单金额 单位为分，只能为整数
     * @param refundFee 退款总金额 单位为分，只能为整数
     * @param refundDesc 退款原因
     * @return
     */
    @ApiOperation(value = "微信退款")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "outTradeNo", value = "商户订单号", paramType = "form", required = true),
            @ApiImplicitParam(name = "totalFeeStr", value = "订单金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "refundFee", value = "退款总金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "refundDesc", value = "退款原因", paramType = "form", required = true)
    })
    @GetMapping("/wx-refund")
    public BusinessMessage shopAndGoods(String outTradeNo, String totalFeeStr, String refundFee, String refundDesc) {
        BusinessMessage message = new BusinessMessage();
        message.setSuccess(false);
        message.setMsg("退款失败");
        Map<String,String> map  = new HashMap<>();
        //商户退款单号
        String outRefundNo = OrderNumGeneration.getOrderIdByUUId();
        map=wxPayService.refund(null,outTradeNo,outRefundNo,totalFeeStr,refundFee,null,refundDesc,null);
        if (map.get("return_msg").equals("OK")) {
            message.setSuccess(true);
            message.setMsg("退款成功");
        }
        return message;
    }
    /**
     * 支付宝退款
     * @param outTradeNo 商户订单号
     * @param refundFee 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @param refundDesc 退款原因
     * @return
     */
    @ApiOperation(value = "支付宝退款")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "outTradeNo", value = "商户订单号", paramType = "form", required = true),
            @ApiImplicitParam(name = "refundFee", value = "退款总金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "refundDesc", value = "退款原因", paramType = "form", required = true)
    })
    @GetMapping("/ali-refund")
    public BusinessMessage shopAndGoods(String outTradeNo, String refundFee, String refundDesc) {
        BusinessMessage message = new BusinessMessage();
        message.setSuccess(false);
        message.setMsg("退款失败");
        AlipayTradeRefundResponse response = aliPayService.refund(outTradeNo,null,refundFee,refundDesc,null,null,null,null);
       System.out.println("@@@@@@@@@@@@@@@@@@@:"+response);
        String strResponse = response.getCode();
        if ("10000".equals(strResponse)) {
            message.setSuccess(true);
            message.setMsg("退款成功");
        }
        return message;
    }
}
