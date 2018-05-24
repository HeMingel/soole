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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            @ApiImplicitParam(name = "payType", value = "支付方式：1-微信；2-支付宝；3-银行卡支付", paramType = "form", required = true)
    })
    @PostMapping("recharge")
    public BusinessMessage<Map> recharge(HttpServletRequest req, double money, int payType) {
        BusinessMessage<Map> message = new BusinessMessage<>();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        log.debug("余额充值，money = {}，payType = {}，userId = {}", money, payType, user.getId());
        if (money <= 0) {
            message.setMsg("支付金额不能小于0元");
        } else if (payType != 1 && payType != 2) {
            message.setMsg("请选择支付方式");
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
}
