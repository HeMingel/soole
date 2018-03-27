package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_activity_product")
public class SlActivityProduct implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 活动唯一标识符
     */
    @Column(name = "activity_id")
    private String activityId;

    /**
     * 活动价格
     */
    private BigDecimal price;

    /**
     * 活动开始时间
     */
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 参与活动的商品数量
     */
    private Integer count;

    /**
     * 拼团人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 预售多少天后发货
     */
    private Integer days;

    /**
     * 已拼单人数
     */
    @Column(name = "orders_num")
    private Integer ordersNum;

    /**
     * 剩余商品数量
     */
    @Column(name = "surplus_product_count")
    private Integer surplusProductCount;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取商品唯一标识符
     *
     * @return product_id - 商品唯一标识符
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品唯一标识符
     *
     * @param productId 商品唯一标识符
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取活动唯一标识符
     *
     * @return activity_id - 活动唯一标识符
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * 设置活动唯一标识符
     *
     * @param activityId 活动唯一标识符
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    /**
     * 获取活动价格
     *
     * @return price - 活动价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置活动价格
     *
     * @param price 活动价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取活动开始时间
     *
     * @return begin_time - 活动开始时间
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param beginTime 活动开始时间
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    /**
     * 获取活动结束时间
     *
     * @return end_time - 活动结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endTime 活动结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    /**
     * 获取参与活动的商品数量
     *
     * @return count - 参与活动的商品数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置参与活动的商品数量
     *
     * @param count 参与活动的商品数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取拼团人数
     *
     * @return people_num - 拼团人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置拼团人数
     *
     * @param peopleNum 拼团人数
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    /**
     * 获取预售多少天后发货
     *
     * @return days - 预售多少天后发货
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 设置预售多少天后发货
     *
     * @param days 预售多少天后发货
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 获取已拼单人数
     *
     * @return orders_num - 已拼单人数
     */
    public Integer getOrdersNum() {
        return ordersNum;
    }

    /**
     * 设置已拼单人数
     *
     * @param ordersNum 已拼单人数
     */
    public void setOrdersNum(Integer ordersNum) {
        this.ordersNum = ordersNum;
    }

    /**
     * 获取剩余商品数量
     *
     * @return surplus_product_count - 剩余商品数量
     */
    public Integer getSurplusProductCount() {
        return surplusProductCount;
    }

    /**
     * 设置剩余商品数量
     *
     * @param surplusProductCount 剩余商品数量
     */
    public void setSurplusProductCount(Integer surplusProductCount) {
        this.surplusProductCount = surplusProductCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", activityId=").append(activityId);
        sb.append(", price=").append(price);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", count=").append(count);
        sb.append(", peopleNum=").append(peopleNum);
        sb.append(", days=").append(days);
        sb.append(", ordersNum=").append(ordersNum);
        sb.append(", surplusProductCount=").append(surplusProductCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}