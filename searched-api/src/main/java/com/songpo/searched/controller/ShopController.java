package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商铺管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/shop")

public class ShopController {
    @Autowired
    private CmShopService cmShopService;

    /**
     * 根据店铺Id查询店铺详情和商品
     * @param shopId 店铺Id
     * @param userId 用户id
     * @param goodsName 商品名称
     * @param sortBySale 根据销量排序
     * @param sortByPrice 根据价格排序
     * @param pageNum
     * @param pageSize
     * @return 店铺商品
     */

    @ApiOperation(value = "查询商铺")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "shopId", value = "商铺Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "form"),
            @ApiImplicitParam(name = "sortBySale", value = "销售数量排序", paramType = "form"),
            @ApiImplicitParam(name = "sortByPrice", value = "价格排序", paramType = "form"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", paramType = "form", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", paramType = "form" )
    })
    @GetMapping("/shop-detail")
    public BusinessMessage shopAndGoods(String shopId,String userId,String goodsName,String sortBySale,String sortByPrice,Integer pageNum,Integer pageSize) {
        if(shopId == null){
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("shopId");
            businessMessage.setSuccess(false);
        }
        return this.cmShopService.shopAndGoods(shopId,userId,goodsName,sortBySale,sortByPrice,pageNum,pageSize);
    }


}
