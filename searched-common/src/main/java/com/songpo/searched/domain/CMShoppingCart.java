package com.songpo.searched.domain;

import lombok.Data;

import java.util.List;

@Data
public class CMShoppingCart {
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品详情
     */
    private List<CMGoods> carts;

}

