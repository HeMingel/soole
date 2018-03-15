package com.songpo.searched.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
public class ShoppingCart {
    /**
     * 商品实体
     */
//    private SlProduct slProduct;
    /**
     * 商品Id
     */
    private String goodId;
    /**
     * 商品名称
     */
//    private String goodName;
    /**
     * 商品图片的Url
     */
//    private String imageUrl;
    /**
     * 商铺Id
     */
    private String shopId;
    /**
     * 商铺名称
     */
    private String shopName;
    /**
     * 价格
     */
//    private String price;
    /**
     * 加入购物车数量
     */
    private int counts;
    /**
     * 规格类型
     */
    private List<String> productTypeName;
}
