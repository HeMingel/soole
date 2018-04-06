package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.SignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            this.signInService.addSignIn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    @ApiOperation(value = "查询签到信息")
    @PostMapping("select-sign-in")
    public BusinessMessage selectSignIn() {
        BusinessMessage message = new BusinessMessage();
        try {
            this.signInService.selectSignIn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
