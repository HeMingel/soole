package com.songpo.searched.domain;

import lombok.Data;

import java.util.List;

@Data
public class CMShoppingCart {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 商品详情
     */
    private List<CMGoods> carts;

    /**
     * 商品实体
     */
//    private SlProduct slProduct;

}

