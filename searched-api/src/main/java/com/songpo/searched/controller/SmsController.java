package com.songpo.searched.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Api(description = "短信接口")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "form")
    })
    @PostMapping("verify-code")
    public BusinessMessage<SendSmsResponse> sendSms(String mobile) {
        return smsService.sendSms(mobile);
    }

    @ApiOperation(value = "发送短信登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "form")
    })
    @PostMapping("pwd")
    public BusinessMessage<SendSmsResponse> sendPassword(String mobile) {
        return smsService.sendPassword(mobile);
    }
}
