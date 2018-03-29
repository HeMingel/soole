package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "sl_activity_product")
public class SlActivityProduct implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品活动序号
     */
    @Column(name = "serial_number")
    private String serialNumber;

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
     * 已拼单人数
     */
    @Column(name = "orders_num")
    private Integer ordersNum;

    /**
     * 剩余商品数量
     */
    @Column(name = "surplus_product_count")
    private Integer surplusProductCount;

    /**
     * 所需了豆
     */
    private Integer peas;

    /**
     * 奖励金豆数量
     */
    @Column(name = "award_peas_counts")
    private Double awardPeasCounts;

    /**
     * 奖励金额
     */
    @Column(name = "awward_money")
    private Double awwardMoney;

    /**
     * 是否启用(1:启用 0;禁用)
     */
    private Boolean enabled;

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
     * 获取商品活动序号
     *
     * @return serial_number - 商品活动序号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置商品活动序号
     *
     * @param serialNumber 商品活动序号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
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

    /**
     * 获取所需了豆
     *
     * @return peas - 所需了豆
     */
    public Integer getPeas() {
        return peas;
    }

    /**
     * 设置所需了豆
     *
     * @param peas 所需了豆
     */
    public void setPeas(Integer peas) {
        this.peas = peas;
    }

    /**
     * 获取奖励金豆数量
     *
     * @return award_peas_counts - 奖励金豆数量
     */
    public Double getAwardPeasCounts() {
        return awardPeasCounts;
    }

    /**
     * 设置奖励金豆数量
     *
     * @param awardPeasCounts 奖励金豆数量
     */
    public void setAwardPeasCounts(Double awardPeasCounts) {
        this.awardPeasCounts = awardPeasCounts;
    }

    /**
     * 获取奖励金额
     *
     * @return awward_money - 奖励金额
     */
    public Double getAwwardMoney() {
        return awwardMoney;
    }

    /**
     * 设置奖励金额
     *
     * @param awwardMoney 奖励金额
     */
    public void setAwwardMoney(Double awwardMoney) {
        this.awwardMoney = awwardMoney;
    }

    /**
     * 获取是否启用(1:启用 0;禁用)
     *
     * @return enabled - 是否启用(1:启用 0;禁用)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用(1:启用 0;禁用)
     *
     * @param enabled 是否启用(1:启用 0;禁用)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", activityId=").append(activityId);
        sb.append(", price=").append(price);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", count=").append(count);
        sb.append(", peopleNum=").append(peopleNum);
        sb.append(", ordersNum=").append(ordersNum);
        sb.append(", surplusProductCount=").append(surplusProductCount);
        sb.append(", peas=").append(peas);
        sb.append(", awardPeasCounts=").append(awardPeasCounts);
        sb.append(", awwardMoney=").append(awwardMoney);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}