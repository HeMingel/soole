package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Slf4j
@Api(description = "商铺管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/shop")

public class ShopController {
    @Autowired
    private CmShopService cmShopService;

    @ApiOperation(value = "查询商铺")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商铺Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "form", required = true)
    })
    @GetMapping("/shop-detail")
    public BusinessMessage shopAndGoods(String id,String userId) {
        if(id == null){
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("id为空");
            businessMessage.setSuccess(false);
        }
        return this.cmShopService.shopAndGoods(id,userId);
    }
}
