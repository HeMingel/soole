package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.SignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "签到管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/sign-in")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @ApiOperation(value = "新增签到天数")
    @PostMapping("add-sign-in")
    public BusinessMessage addSignIn() {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.signInService.addSignIn();
            message.setSuccess(message.getSuccess());
            message.setMsg(message.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    @ApiOperation(value = "查询签到信息")
    @GetMapping("select-sign-in")
    public BusinessMessage selectSignIn() {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.signInService.selectSignIn();
            message.setData(message.getData());
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
