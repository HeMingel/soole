package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmOrderService;
import com.songpo.searched.service.ThirdPartyWalletService;
import com.songpo.searched.util.SLStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

/**
 * 钱包APP支付接口
 */
@Api(description = "钱包APP支付")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/wallet")
public class ThirdPartyWalletController {

    @Autowired
    private ThirdPartyWalletService thirdPartyWalletService;
    @Autowired
    private CmOrderService cmOrderService;

    @ApiOperation(value = "钱包SLB支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "walletAddress", value = "商户订单号", paramType = "form", required = true),
            @ApiImplicitParam(name = "walletPwd", value = "订单金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "amount", value = "退款总金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "orderSn", value = "退款原因", paramType = "form", required = true)
    })
    @PostMapping("pay")
    public BusinessMessage paySLBFromWallet(String walletAddress,String walletPwd,String amount ,String orderSn){
        BusinessMessage message = new BusinessMessage();
        //非空校验
        if (SLStringUtils.isEmpty(walletAddress)){
            message.setMsg("钱包地址不能为空");
        } else if (SLStringUtils.isEmpty(walletPwd)){
            message.setMsg("钱包密码不能为空");
        } else if (SLStringUtils.isEmpty(amount)) {
            message.setMsg("支付金额不能为空");
        } else if (SLStringUtils.isEmpty(orderSn)) {
            message.setMsg("订单号不能为空");
        } else{
            BigDecimal money = new BigDecimal(amount);
            Integer code = thirdPartyWalletService.paySlbAmount(walletAddress,walletPwd,money,orderSn);
            if (code == 0) {
                message.setMsg("SLB支付成功");
                message.setSuccess(true);
            }else {
                message.setMsg("SLB支付失败");
                message.setCode(code.toString());
            }
        }
        return  message;
    }

    @ApiOperation(value = "7.12前订单SLB转入")
    @GetMapping("transfer-before")
    public void transferSlbToWalletBefore(){
        cmOrderService.transferSlbToWalletBefore();
    }
    @ApiOperation(value = "7.12后订单SLB转入")
    @GetMapping("transfer-after")
    public void transferSlbToWalletAfter(){
        cmOrderService.transferSlbToWalletAfter();
    }
    @ApiOperation(value = "用户注册送的SLB")
    @GetMapping("transfer-user")
    public void transferSlbToWalletUser(){
        cmOrderService.transferSlbToWalletUser();
    }

    @ApiOperation(value = "紧急处理脏数据")
    @GetMapping("urgency-slb-transaction")
    public void urgencySlbTransaction(){
        thirdPartyWalletService.urgencySlbTransaction();
    }

}
