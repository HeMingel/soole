package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CustomerClientHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户的API控制层
 *
 * @author 刘松坡
 */
@Slf4j
@Api(description = "用户端管理")
@RestController
@RequestMapping("/api/v1/customer-client")
public class CustomerClientController {

    @Autowired
    private CustomerClientHomeService customerClientHomeService;


    /**
     * 获取首页所有数据
     *
     * @return
     */
    @ApiOperation("用户端首页")
    @GetMapping("home")
    public BusinessMessage<JSONObject> home() {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            message.setData(this.customerClientHomeService.getHomeData());
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("获取用户端首页数据失败，{}", e);

            message.setMsg("获取数据失败，请重试");
        }
        return message;
    }

    /**
     * 获取用户端分类数据
     *
     * @return
     */
    @ApiOperation("用户端分类")
    @GetMapping("classification")
    public BusinessMessage<JSONObject> classification() {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            message.setData(this.customerClientHomeService.getClassificationData());
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("获取用户端分类数据失败，{}", e);

            message.setMsg("获取数据失败，请重试");
        }
        return message;
    }

    /**
     * 获取用户端购物车数据
     *
     * @return
     */
    @ApiOperation("用户端购物车")
    @GetMapping("shopping-cart")
    public BusinessMessage<JSONObject> shoppingCart(String uid) {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            message.setData(this.customerClientHomeService.getShoppingCartData(uid));
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("获取用户端购物车数据失败，{}", e);

            message.setMsg("获取数据失败，请重试");
        }
        return message;
    }
}