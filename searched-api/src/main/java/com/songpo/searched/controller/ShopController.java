package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmShopService;
import com.songpo.searched.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {

    private CmShopService cmShopService;

    @GetMapping("id")
    public BusinessMessage shopAndGoods(String id) {
        return this.cmShopService.shopAndGoods(id);
    }
}
