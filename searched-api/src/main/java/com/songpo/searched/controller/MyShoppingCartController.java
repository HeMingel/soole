package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.service.MyShoppingCartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "购物车管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/myShoppingCart")
public class MyShoppingCartController {

    @Autowired
    private MyShoppingCartService shoppingCartService;

    /**
     * 购物车添加
     *
     * @param pojo
     */
    @PostMapping("add")
    public BusinessMessage addmyShoppingCart(CMShoppingCart pojo) {
        return this.shoppingCartService.addmyShoppingCart(pojo);
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping("serch")
    public BusinessMessage findCart(String uid) {
        return this.shoppingCartService.findCart(uid);
    }

}

