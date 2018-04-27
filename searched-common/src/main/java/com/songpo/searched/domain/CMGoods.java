package com.songpo.searched.domain;

import java.math.BigDecimal;

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
    /**
     * 该商品限制数量
     */
    private Integer restrictCount;
    /**
     * 商品的活动id
     */
    private String activityId;
    /**
     * 邮费
     */
    private BigDecimal postAge;


    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getSaleType() {
        return saleType;
    }

    public void setSaleType(int saleType) {
        this.saleType = saleType;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getRemainingqty() {
        return remainingqty;
    }

    public void setRemainingqty(Integer remainingqty) {
        this.remainingqty = remainingqty;
    }

    public Integer getRebatePulse() {
        return rebatePulse;
    }

    public void setRebatePulse(Integer rebatePulse) {
        this.rebatePulse = rebatePulse;
    }

    public Integer getMyBeansCounts() {
        return myBeansCounts;
    }

    public void setMyBeansCounts(Integer myBeansCounts) {
        this.myBeansCounts = myBeansCounts;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public Integer getRestrictCount() {
        return restrictCount;
    }

    public void setRestrictCount(Integer restrictCount) {
        this.restrictCount = restrictCount;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getPostAge() {
        return postAge;
    }

    public void setPostAge(BigDecimal postAge) {
        this.postAge = postAge;
    }
}
