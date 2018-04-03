package com.songpo.searched.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
     * 销售类型
     */
    private int saleType;
    /**
     * 银豆
     */
    private int silver;
    /**
     * 商品标签Id
     */
    private String specificationId;
    /**
     * 规格名称
     */
    private String specificationName;
    /**
     * 店铺仓库的ID
     */
    private String repositoryId;
    /**
     * 仓库商品剩余数量
     */
    private Integer remainingqty;
    /**
     * 返了豆数量
     */
    private Integer rebatePulse;
    /**
     * 我的豆子总和
     */
    private Integer myBeansCounts;
    /**
     * 是否下架
     */
    private boolean soldOut;


}
