package com.songpo.searched.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author 刘松坡
 */
@Api(description = "短信接口")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsPasswordCache smsPasswordCache;

    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "form"),
            @ApiImplicitParam(name = "zone", value = "地区", paramType = "form")
    })
    @PostMapping("verify-code")
    public BusinessMessage<SendSmsResponse> sendSms(String mobile, String zone) {
        return smsService.sendSms(mobile,zone);
    }

    @ApiOperation(value = "发送短信登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "form"),
            @ApiImplicitParam(name = "zone", value = "地区", paramType = "form")
    })
    @PostMapping("pwd")
    public BusinessMessage<SendSmsResponse> sendPassword(String mobile, String zone) {
        return smsService.sendPassword(mobile, zone);
    }

    @ApiOperation(value = "手动生成验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "form"),
            @ApiImplicitParam(name = "zone", value = "地区", paramType = "form")
    })
    @PostMapping("code")
    public BusinessMessage sendCode(String mobile) {
        BusinessMessage businessMessage = new BusinessMessage();
        String [] strings = mobile.split(",");
        try {
            for (int i = 0; i<strings.length; i++){
                this.smsPasswordCache.put(strings[i], "123456", 120L, TimeUnit.MINUTES);
            }
            businessMessage.setMsg("生成验证码成功");
            businessMessage.setSuccess(true);
        }catch (Exception e){
            businessMessage.setMsg(e.toString());
            businessMessage.setSuccess(false);
        }
       return businessMessage;
    }
}
