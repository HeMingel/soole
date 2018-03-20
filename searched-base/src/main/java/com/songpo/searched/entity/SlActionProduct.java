package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_action_product")
public class SlActionProduct implements Serializable {
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
    @Column(name = "action_id")
    private String actionId;

    /**
     * 活动价格
     */
    private Long price;

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
     * @return action_id - 活动唯一标识符
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * 设置活动唯一标识符
     *
     * @param actionId 活动唯一标识符
     */
    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }

    /**
     * 获取活动价格
     *
     * @return price - 活动价格
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置活动价格
     *
     * @param price 活动价格
     */
    public void setPrice(Long price) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", actionId=").append(actionId);
        sb.append(", price=").append(price);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", count=").append(count);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}