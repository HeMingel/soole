package com.songpo.searched.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "第三方支付")
@RestController
@RequestMapping("/api/common/v1/tppay")
public class ThirdPartyPayController {
    public static final Logger log = LoggerFactory.getLogger(NotifyController.class);
}
