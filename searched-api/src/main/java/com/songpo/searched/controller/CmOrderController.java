package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMSlOrderDetail;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.service.CmOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@Api(description = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/order")
public class CmOrderController {

    @Autowired
    private CmOrderService cmOrderService;

    /**
     * 预下单订单
     *
     * @param slOrder
     * @param cmSlOrderDetail
     * @return
     */
    @ApiOperation(value = "添加预下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", required = true),
            @ApiImplicitParam(name = "shippingAddressId", value = "地址Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "repositoryId", value = "店铺仓库唯一标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "quantity", value = "订单商品数量", paramType = "form", required = true),
            @ApiImplicitParam(name = "paymentState", value = "支付状态(0：待支付1：支付成功 2：支付失败)", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "订单类型(1：普通订单2：拼团订单3:预售订单)", paramType = "form", required = true),
            @ApiImplicitParam(name = "paymentChannel", value = "支付类型(1：微信支付 2：支付宝支付 3：厦门银行支付)", paramType = "form"),
            @ApiImplicitParam(name = "payTime", value = "支付时间", paramType = "form"),
            @ApiImplicitParam(name = "buyerMessage", value = "买家留言", paramType = "form"),
            @ApiImplicitParam(name = "price", value = "单个商品的价格", paramType = "form"),
            @ApiImplicitParam(name = "deductPulse", value = "单个商品需扣除的金豆", paramType = "form"),
            @ApiImplicitParam(name = "postFee", value = "邮费", paramType = "form")
    })
    @PostMapping("add")
    public BusinessMessage addOrder(SlOrder slOrder, CMSlOrderDetail cmSlOrderDetail) {
        return this.cmOrderService.addOrder(slOrder, cmSlOrderDetail);
    }

    /**
     * 我的订单列表
     *
     * @param oAuth2Authentication
     * @return
     */
    @ApiOperation(value = "我的订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oAuth2Authentication", value = "token", paramType = "form", required = true)
    })
    @GetMapping("list")
    public BusinessMessage list(OAuth2Authentication oAuth2Authentication) {
        return this.cmOrderService.findList(oAuth2Authentication.getOAuth2Request().getClientId());
    }

}
