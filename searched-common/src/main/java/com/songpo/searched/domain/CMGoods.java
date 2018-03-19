package com.songpo.searched.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CMGoods {
    /**
     * 商品实体
     */
//    private SlProduct slProduct;
    /**
     * 商品Id
     */
    /**
     * 商品Id
     */
    private String goodId;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 商品图片的Url
     */
    private String imageUrl;
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
    private BigDecimal price;
    /**
     * 加入购物车数量
     */
    private int counts;
    /**
     * 规格名称
     */
    private String tagName;
    /**
     * 销售类型
     */
    private int saleType;
    /**
     * 了豆
     */
    private int pulse;
    /**
     * 商品标签Id
     */
    private String specificationId;
    /**
     * 店铺仓库的ID
     */
    private String repository;

}
