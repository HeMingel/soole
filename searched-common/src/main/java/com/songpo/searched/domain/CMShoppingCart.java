package com.songpo.searched.domain;

import java.util.List;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CMGoods> getCarts() {
        return carts;
    }

    public void setCarts(List<CMGoods> carts) {
        this.carts = carts;
    }
}

