package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.service.CustomerClientHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户端的API控制层
 * <p>
 * 用于配置用户端APP每个页面所有数据统一的接口
 * 比如首页有商品一级分类、拼团秒杀活动等，将这些数据放到一个接口里
 *
 * @author 刘松坡
 */
@Api(description = "用户端管理")
@RestController
@RequestMapping("/api/common/v1/customer-client")
public class CustomerClientController {

    public static final Logger log = LoggerFactory.getLogger(CustomerClientController.class);

    @Autowired
    private CustomerClientHomeService customerClientHomeService;


    /**
     * 获取首页所有数据
     *
     * @return
     */
    @ApiOperation(value = "用户端首页", notes = "用于获取首页所有数据")
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
    public BusinessMessage shoppingCart(String uid) {
        BusinessMessage message = new BusinessMessage<>();
        try {
            message = this.customerClientHomeService.getShoppingCartData(uid);
            message.setSuccess(message.getSuccess());
            message.setData(message.getData());
            message.setMsg(message.getMsg());
        } catch (Exception e) {
            log.error("获取用户端购物车数据失败，{}", e);

            message.setMsg("获取数据失败，请重试");
        }
        return message;
    }

    /**
     * 分页查询首页推荐商品接口
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("获取推荐商品")
    @PostMapping("list-recommend")
    public BusinessMessage getRecommendProduct(Integer pageNum,Integer pageSize ){
        BusinessMessage message = new BusinessMessage<>();
        try{
            message = this.customerClientHomeService.getRecommendProduct(pageNum, pageSize);
                message.setSuccess(true);
                message.setMsg("获取数据成功");
        } catch (Exception e) {
            log.error("获取首页推荐商品，{}", e);
            message.setMsg("获取数据失败，请重试");
        }
       return message;
    }
}
