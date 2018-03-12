package com.songpo.domain;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class ProductDto {

    private String productId;

    /**
     * 名称
     */
    private String name;

    /**
     * 平台价格
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String imageUrl;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 详情
     */
    private String detail;

    /**
     * 参考价格
     */
    private BigDecimal referencePrice;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 拼团价
     */
    private BigDecimal collagePrice;

    /**
     * 个人价
     */

    private BigDecimal personalPrice;

    /**
     * 秒杀价
     */
    private BigDecimal priceSpike;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 是否包邮
     */
    private Boolean isship;
    /**
     * 评论总数
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 销售总数
     */
    @Column(name = "sale_num")
    private Integer saleNum;

    private String productTypeId;
    /**
     * 销售类型
     */
    private Integer saleType;
}
