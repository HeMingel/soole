package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_slb_transaction")
public class SlSlbTransaction implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 对方id（转账）
     */
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 当前用户id
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 搜了贝类型
     */
    @Column(name = "slb_type")
    private Integer slbType;

    /**
     * 消费方式 1转账 2购物 3邀请人获得 4微信网页注册赠送
     */
    private Integer type;

    /**
     * 交易搜了贝数量
     */
    private BigDecimal slb;

    /**
     * 交易类型  1.支出  2.收入
     */
    @Column(name = "transaction_type")
    private Integer transactionType;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取对方id（转账）
     *
     * @return source_id - 对方id（转账）
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置对方id（转账）
     *
     * @param sourceId 对方id（转账）
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * 获取当前用户id
     *
     * @return target_id - 当前用户id
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 设置当前用户id
     *
     * @param targetId 当前用户id
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取搜了贝类型
     *
     * @return slb_type - 搜了贝类型
     */
    public Integer getSlbType() {
        return slbType;
    }

    /**
     * 设置搜了贝类型
     *
     * @param slbType 搜了贝类型
     */
    public void setSlbType(Integer slbType) {
        this.slbType = slbType;
    }

    /**
     * 获取消费方式 1转账 2购物 3邀请人获得 4微信网页注册赠送
     *
     * @return type - 消费方式 1转账 2购物 3邀请人获得 4微信网页注册赠送
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消费方式 1转账 2购物 3邀请人获得 4微信网页注册赠送
     *
     * @param type 消费方式 1转账 2购物 3邀请人获得 4微信网页注册赠送
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取交易搜了贝数量
     *
     * @return slb - 交易搜了贝数量
     */
    public BigDecimal getSlb() {
        return slb;
    }

    /**
     * 设置交易搜了贝数量
     *
     * @param slb 交易搜了贝数量
     */
    public void setSlb(BigDecimal slb) {
        this.slb = slb;
    }

    /**
     * 获取交易类型  1.支出  2.收入
     *
     * @return transaction_type - 交易类型  1.支出  2.收入
     */
    public Integer getTransactionType() {
        return transactionType;
    }

    /**
     * 设置交易类型  1.支出  2.收入
     *
     * @param transactionType 交易类型  1.支出  2.收入
     */
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
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
     * 获取最后更新时间
     *
     * @return updated_at - 最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updatedAt 最后更新时间
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
        sb.append(", sourceId=").append(sourceId);
        sb.append(", targetId=").append(targetId);
        sb.append(", orderId=").append(orderId);
        sb.append(", slbType=").append(slbType);
        sb.append(", type=").append(type);
        sb.append(", slb=").append(slb);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}