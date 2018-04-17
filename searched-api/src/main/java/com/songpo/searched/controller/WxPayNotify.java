package com.songpo.searched.controller;
import com.songpo.searched.service.WxXmlVerify;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(description = "微信支付接口")
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api/common/v1/wx-pay-notify")
public class WxPayNotify {

    @Autowired
    private WxXmlVerify wxXmlVerify;

    @PostMapping()
    public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        return this.wxXmlVerify.verify(request, response);
    }

}
