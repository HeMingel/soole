package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.service.AccountService;
import com.songpo.searched.service.LoginUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Api(description = "账户管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/account")
public class AccountController {

    public static final Logger log = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private AccountService accountService;

    /**
     * 余额充值
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "余额充值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "money", value = "充值金额", paramType = "form", required = true),
            @ApiImplicitParam(name = "payType", value = "支付方式：1-微信APP；2-微信H5；3-支付宝APP；4-支付宝H5；5-银行卡支付", paramType = "form", required = true)
    })
    @PostMapping("recharge")
    public BusinessMessage<Map> recharge(HttpServletRequest req, BigDecimal money, int payType) {
        BusinessMessage<Map> message = new BusinessMessage<>();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        log.debug("余额充值，money = {}，payType = {}，userId = {}", money, payType, user.getId());
        if (money.compareTo(new BigDecimal(0)) <= 0) {
            message.setMsg("支付金额不能小于0元");
        } else {
            try {
                message = accountService.recharge(req, money, payType);
            } catch (Exception e) {
                log.error("余额充值异常", e);
                message.setMsg("充值异常：" + e.getMessage());
            }
        }

        return message;
    }

    /**
     * 余额充值通知
     * 1、余额充值通知
     * 2、调用支付查询接口，查询是否支付成功并处理
     *
     * @param outTradeNo 商户订单号
     * @param payType
     * @return
     */
    @ApiOperation(value = "余额充值通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outTradeNo", value = "商户订单号", paramType = "form", required = true),
            @ApiImplicitParam(name = "payType", value = "支付方式：1-微信APP；2-微信H5；3-支付宝APP；4-支付宝H5；5-银行卡支付", paramType = "form", required = true)
    })
    @GetMapping("notify")
    public BusinessMessage rechargeNotify(String outTradeNo, int payType) {
        BusinessMessage message = new BusinessMessage<>();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        log.debug("余额，outTradeNo = {}，payType = {}，userId = {}", outTradeNo, payType, user.getId());
        try {
            message = accountService.rechargeNotify(outTradeNo, payType);
        } catch (Exception e) {
            log.error("余额充值异常", e);
            message.setMsg("充值异常：" + e.getMessage());
        }

        return message;
    }

    /**
     * 根据ID获取用户详情
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据ID获取用户详情")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", required = true)
    @PostMapping("getUserInfo")
    public BusinessMessage getUserInfo(String userId) {
        BusinessMessage message  = accountService.getUserInfo(userId);
        return message;
    }


    /**
     * 根据ID 操作用户金豆
     * @param userId
     * @param number
     * @param type   0: 增加 1:扣除
     * @return
     */
    @ApiOperation(value="操作用户金豆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", required = true),
            @ApiImplicitParam(name = "number", value = "需要操作的豆子", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "操作类型 0: 增加 1:扣除 ", paramType = "form", required = true)
    })
    @PostMapping("operateCoinById")
    public BusinessMessage operateCoinById (String userId,Integer number,Integer type) {
        BusinessMessage  message = accountService.operateCoinById(userId,number,type);
        return message;
    }
}
