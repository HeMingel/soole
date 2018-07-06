package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_total_pool_detail")
public class SlTotalPoolDetail implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "pd_user_id")
    private String pdUserId;

    /**
     * 订单ID
     */
    @Column(name = "pd_order_id")
    private String pdOrderId;

    /**
     * 交易类型(针对纯系统产生的豆和贝) 
1用户注册(银豆)  
2用户签到(金豆)  
3购物分享(针对现金购物分享赠送的银豆) 
4邀请好友(金豆，此时的pd_user_id为被邀请人的id)  
5交易购物(针对购买的搜了贝)  
6水果竞猜(金豆)  
7券兑换银豆(银豆)
8给邀请人发送搜了贝
     */
    @Column(name = "pd_type")
    private Integer pdType;

    /**
     * 交易金豆额度
     */
    @Column(name = "pd_coin")
    private Integer pdCoin;

    /**
     * 交易银豆额度
     */
    @Column(name = "pd_silver")
    private Integer pdSilver;

    /**
     * 交易搜了贝额度
     */
    @Column(name = "pd_slb")
    private BigDecimal pdSlb;

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

    private static final long serialVersionUID = 1L;

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
     * 获取用户ID
     *
     * @return pd_user_id - 用户ID
     */
    public String getPdUserId() {
        return pdUserId;
    }

    /**
     * 设置用户ID
     *
     * @param pdUserId 用户ID
     */
    public void setPdUserId(String pdUserId) {
        this.pdUserId = pdUserId == null ? null : pdUserId.trim();
    }

    /**
     * 获取订单ID
     *
     * @return pd_order_id - 订单ID
     */
    public String getPdOrderId() {
        return pdOrderId;
    }

    /**
     * 设置订单ID
     *
     * @param pdOrderId 订单ID
     */
    public void setPdOrderId(String pdOrderId) {
        this.pdOrderId = pdOrderId == null ? null : pdOrderId.trim();
    }

    /**
     * 获取交易类型(针对纯系统产生的豆和贝) 
1用户注册(银豆)  
2用户签到(金豆)  
3购物分享(针对现金购物分享赠送的银豆) 
4邀请好友(金豆，此时的pd_user_id为被邀请人的id)  
5交易购物(针对购买的搜了贝)  
6水果竞猜(金豆)  
7券兑换银豆(银豆)
8给邀请人发送搜了贝
     *
     * @return pd_type - 交易类型(针对纯系统产生的豆和贝) 
1用户注册(银豆)  
2用户签到(金豆)  
3购物分享(针对现金购物分享赠送的银豆) 
4邀请好友(金豆，此时的pd_user_id为被邀请人的id)  
5交易购物(针对购买的搜了贝)  
6水果竞猜(金豆)  
7券兑换银豆(银豆)
8给邀请人发送搜了贝
     */
    public Integer getPdType() {
        return pdType;
    }

    /**
     * 设置交易类型(针对纯系统产生的豆和贝) 
1用户注册(银豆)  
2用户签到(金豆)  
3购物分享(针对现金购物分享赠送的银豆) 
4邀请好友(金豆，此时的pd_user_id为被邀请人的id)  
5交易购物(针对购买的搜了贝)  
6水果竞猜(金豆)  
7券兑换银豆(银豆)
8给邀请人发送搜了贝
     *
     * @param pdType 交易类型(针对纯系统产生的豆和贝) 
1用户注册(银豆)  
2用户签到(金豆)  
3购物分享(针对现金购物分享赠送的银豆) 
4邀请好友(金豆，此时的pd_user_id为被邀请人的id)  
5交易购物(针对购买的搜了贝)  
6水果竞猜(金豆)  
7券兑换银豆(银豆)
8给邀请人发送搜了贝
     */
    public void setPdType(Integer pdType) {
        this.pdType = pdType;
    }

    /**
     * 获取交易金豆额度
     *
     * @return pd_coin - 交易金豆额度
     */
    public Integer getPdCoin() {
        return pdCoin;
    }

    /**
     * 设置交易金豆额度
     *
     * @param pdCoin 交易金豆额度
     */
    public void setPdCoin(Integer pdCoin) {
        this.pdCoin = pdCoin;
    }

    /**
     * 获取交易银豆额度
     *
     * @return pd_silver - 交易银豆额度
     */
    public Integer getPdSilver() {
        return pdSilver;
    }

    /**
     * 设置交易银豆额度
     *
     * @param pdSilver 交易银豆额度
     */
    public void setPdSilver(Integer pdSilver) {
        this.pdSilver = pdSilver;
    }

    /**
     * 获取交易搜了贝额度
     *
     * @return pd_slb - 交易搜了贝额度
     */
    public BigDecimal getPdSlb() {
        return pdSlb;
    }

    /**
     * 设置交易搜了贝额度
     *
     * @param pdSlb 交易搜了贝额度
     */
    public void setPdSlb(BigDecimal pdSlb) {
        this.pdSlb = pdSlb;
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
        sb.append(", pdUserId=").append(pdUserId);
        sb.append(", pdOrderId=").append(pdOrderId);
        sb.append(", pdType=").append(pdType);
        sb.append(", pdCoin=").append(pdCoin);
        sb.append(", pdSilver=").append(pdSilver);
        sb.append(", pdSlb=").append(pdSlb);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}