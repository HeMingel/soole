package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_check_order")
public class SlCheckOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 订单详情表ID
     */
    @Column(name = "order_detail_id")
    private String orderDetailId;
    /**
     * 是否确认1未确认2已确认
     */
    @Column(name = "co_state")
    private Boolean coState;
    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取订单详情表ID
     *
     * @return order_detail_id - 订单详情表ID
     */
    public String getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * 设置订单详情表ID
     *
     * @param orderDetailId 订单详情表ID
     */
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId == null ? null : orderDetailId.trim();
    }

    /**
     * 获取是否确认1未确认2已确认
     *
     * @return co_state - 是否确认1未确认2已确认
     */
    public Boolean getCoState() {
        return coState;
    }

    /**
     * 设置是否确认1未确认2已确认
     *
     * @param coState 是否确认1未确认2已确认
     */
    public void setCoState(Boolean coState) {
        this.coState = coState;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", coState=").append(coState);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}