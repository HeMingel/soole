package com.songpo.searched.controller;

import com.songpo.searched.service.PaymentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Slf4j
@Api(description = "用户端管理")
@RestController
@RequestMapping("/api/common/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
}
