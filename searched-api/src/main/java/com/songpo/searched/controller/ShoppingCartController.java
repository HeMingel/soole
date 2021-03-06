package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(description = "购物车管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/my-shopping-cart")
public class ShoppingCartController {

    public static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 购物车添加
     *
     * @param repositoryId
     * @param counts
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
            @ApiImplicitParam(name = "oAuth2Authentication", value = "token", paramType = "form", required = true)
    })
    @PostMapping("add")
    public BusinessMessage addMyShoppingCart(String repositoryId, int counts, String activityId) {
        BusinessMessage message = new BusinessMessage();
        if (!StringUtils.isEmpty(repositoryId) && !StringUtils.isEmpty(counts)) {
            CMShoppingCart pojo = new CMShoppingCart();
            List<CMGoods> list = new ArrayList<>();
            CMGoods goods = new CMGoods();
            goods.setRepositoryId(repositoryId);
            goods.setCounts(counts);
            goods.setActivityId(activityId);
            list.add(goods);
            pojo.setCarts(list);
            message = this.shoppingCartService.addMyShoppingCart(pojo);
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
            message.setData(message.getData());
        } else {
            message.setMsg("传入的参数为空");
        }
        return message;
    }

    @ApiOperation(value = "删除购物车的某个商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "repositoryId", value = "规格id", paramType = "form", required = true)
    })
    @PostMapping("delete-shopping-carts")
    public BusinessMessage deleteMyShoppingCart(String[] repositoryId) {
        log.debug("repositoryId = [" + repositoryId + "]");
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.shoppingCartService.deleteMyShoppingCart(repositoryId);
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败", e);
        }
        return message;
    }

    @ApiOperation(value = "编辑商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "repository", value = "之前的规格id", paramType = "form")
    })
    @PostMapping("edit-shopping-carts")
    public BusinessMessage editShoppingCarts(String[] repository) {
        log.debug("repository = [" + repository + "]");
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.shoppingCartService.editShoppingCarts(repository);
            message.setSuccess(message.getSuccess());
            message.setMsg(message.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改失败", e);
        }
        return message;
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping("serch")
    @ApiOperation(value = "查询购物车")
    public BusinessMessage findCart() {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.shoppingCartService.findCart();
            message.setSuccess(message.getSuccess());
            message.setMsg(message.getMsg());
            message.setData(message.getData());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败", e);
        }
        return message;
    }

}

