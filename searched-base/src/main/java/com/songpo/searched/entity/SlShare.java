package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_share")
public class SlShare implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 订单id（订单支付完成分享使用）
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * app分享后打开的h5页面，拆红包显示的银豆数量
     */
    private Integer silver;

    /**
     * 分享类型 
1. 购物完成分享订单（第一次）  
2. 购物完成分享订单（第二次）  
3. h5页面拆红包获得的银豆数量（用户每天拆一次红包，会生成一条记录，可以根据有几条记录判断用户当天拆红包次数）
     */
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 每次更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取订单id（订单支付完成分享使用）
     *
     * @return order_id - 订单id（订单支付完成分享使用）
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id（订单支付完成分享使用）
     *
     * @param orderId 订单id（订单支付完成分享使用）
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取app分享后打开的h5页面，拆红包显示的银豆数量
     *
     * @return silver - app分享后打开的h5页面，拆红包显示的银豆数量
     */
    public Integer getSilver() {
        return silver;
    }

    /**
     * 设置app分享后打开的h5页面，拆红包显示的银豆数量
     *
     * @param silver app分享后打开的h5页面，拆红包显示的银豆数量
     */
    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    /**
     * 获取分享类型 
1. 购物完成分享订单（第一次）  
2. 购物完成分享订单（第二次）  
3. h5页面拆红包获得的银豆数量（用户每天拆一次红包，会生成一条记录，可以根据有几条记录判断用户当天拆红包次数）
     *
     * @return type - 分享类型 
1. 购物完成分享订单（第一次）  
2. 购物完成分享订单（第二次）  
3. h5页面拆红包获得的银豆数量（用户每天拆一次红包，会生成一条记录，可以根据有几条记录判断用户当天拆红包次数）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置分享类型 
1. 购物完成分享订单（第一次）  
2. 购物完成分享订单（第二次）  
3. h5页面拆红包获得的银豆数量（用户每天拆一次红包，会生成一条记录，可以根据有几条记录判断用户当天拆红包次数）
     *
     * @param type 分享类型 
1. 购物完成分享订单（第一次）  
2. 购物完成分享订单（第二次）  
3. h5页面拆红包获得的银豆数量（用户每天拆一次红包，会生成一条记录，可以根据有几条记录判断用户当天拆红包次数）
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取每次更新时间
     *
     * @return updated_at - 每次更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置每次更新时间
     *
     * @param updatedAt 每次更新时间
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
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", silver=").append(silver);
        sb.append(", type=").append(type);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}