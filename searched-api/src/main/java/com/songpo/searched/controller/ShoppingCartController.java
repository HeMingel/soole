package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

@Api(description = "购物车管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/my-shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 购物车添加
     *
     * @param pojo
     */
    @ApiOperation(value = "添加购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", required = true),
            @ApiImplicitParam(name = "goodId", value = "商品Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "goodName", value = "商品名称", paramType = "form"),
            @ApiImplicitParam(name = "imageUrl", value = "商品图片的Url", paramType = "form"),
            @ApiImplicitParam(name = "shopId", value = "商铺Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "shopName", value = "商铺名称", paramType = "form", required = true),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "form"),
            @ApiImplicitParam(name = "counts", value = "加入购物车数量", paramType = "form", required = true),
            @ApiImplicitParam(name = "saleType", value = "销售类型", paramType = "form"),
            @ApiImplicitParam(name = "pulse", value = "了豆", paramType = "form"),
            @ApiImplicitParam(name = "specificationId", value = "商品规格Id", paramType = "form"),
            @ApiImplicitParam(name = "repositoryId", value = "店铺仓库的ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "specificationName", value = "规格名称", paramType = "form"),
            @ApiImplicitParam(name = "oAuth2Authentication", value = "token", paramType = "form",required = true)
    })
    @PostMapping("add")
    public BusinessMessage addmyShoppingCart(CMShoppingCart pojo,OAuth2Authentication oAuth2Authentication) {
        return this.shoppingCartService.addmyShoppingCart(pojo,oAuth2Authentication.getOAuth2Request().getClientId());
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping("serch")
    @ApiOperation(value = "查询购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户id", paramType = "form", required = true),
    })
    public BusinessMessage findCart(OAuth2Authentication oAuth2Authentication) {
        return this.shoppingCartService.findCart(oAuth2Authentication.getOAuth2Request().getClientId());
    }

}

