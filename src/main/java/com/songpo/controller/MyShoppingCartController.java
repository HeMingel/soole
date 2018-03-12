package com.songpo.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.songpo.cache.ShoppingCartCache;
import com.songpo.domain.BusinessMessage;
import com.songpo.domain.MyShoppingCartPojo;
import com.songpo.domain.ShoppingCart;
import com.songpo.entity.SlProduct;
import com.songpo.entity.SlUser;
import com.songpo.service.MyShoppingCartService;
import com.songpo.service.UserService;
import com.songpo.validator.OrderValidator;
import io.swagger.annotations.Api;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(description = "购物车管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/myShoppingCart")
public class MyShoppingCartController {

    @Autowired
    private ShoppingCartCache cache;
    @Autowired
    private UserService userService;

    /**
     * 购物车添加
     *
     * @param pojo
     */
    @PostMapping("add")
    public BusinessMessage<Json> addmyShoppingCart(MyShoppingCartPojo pojo) {
        BusinessMessage message = new BusinessMessage(false);
        if (StringUtils.isEmpty(pojo.getUserId()))
        {
            message.setMsg("用户ID为空");
        } else
        {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(pojo.getUserId());
            }});
            if (null != user)
            {
                this.cache.put(pojo.getUserId(), pojo);
                message.setMsg("添加成功");
                message.setSuccess(true);
                message.setData(1);

            } else
            {
                message.setMsg("传入的用户ID不存在");
            }
        }
        return message;
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping("serch")
    public BusinessMessage<MyShoppingCartPojo> findCart(String uid) {
        BusinessMessage message = new BusinessMessage(false);
        message.setMsg("查询成功");
        if (StringUtils.isEmpty(uid))
        {
            message.setMsg("用户ID为空");
            message.setData(null);
        } else
        {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user)
            {
                MyShoppingCartPojo pojo = this.cache.get(uid);
                for (ShoppingCart sc : pojo.getCarts()){
//                    SlProduct slProduct = this. sc.getGoodId();
                }
                if (StringUtils.isEmpty(pojo))
                {
                    message.setMsg("查询状态为空");
                    message.setData(null);
                    message.setSuccess(true);
                } else
                {
                    message.setData(pojo);
                    message.setSuccess(true);
                    message.setMsg("查询成功");
                }
            } else
            {
                message.setMsg("传入用户ID不存在");
            }
        }
        return message;
    }
}
