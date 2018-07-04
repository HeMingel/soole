package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_red_packet")
public class SlRedPacket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    /**
     * 红包金额/ 了豆个数
     */
    private BigDecimal money;

    /**
     * 剩余红包金额/剩余了豆个数
     */
    @Column(name = "surplus_money")
    private BigDecimal surplusMoney;

    /**
     * 红包个数
     */
    private Integer count;

    /**
     * 剩余红包个数
     */
    @Column(name = "surplus_count")
    private Integer surplusCount;

    /**
     * 红包类型   1. 余额，  2.豆
     */
    private Boolean type;

    /**
     * 红包类型  1.单聊红包   2.群红包  3.消费红包(分享奖励商品)  4.分享红包(分享奖励商品)
     */
    @Column(name = "red_packet_type")
    private Byte redPacketType;

    /**
     * 红包结果  1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) 6.已领取(分享奖励商品)
     */
    private Byte result;

    /**
     * 红包发送时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 红包抢完时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 红包过期时间
     */
    @Column(name = "timed_time")
    private Date timedTime;

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

    /**
     * 分享-订单表的主键
     */
    @Column(name = "order_extend_id")
    private String orderExtendId;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取红包金额/ 了豆个数
     *
     * @return money - 红包金额/ 了豆个数
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置红包金额/ 了豆个数
     *
     * @param money 红包金额/ 了豆个数
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取剩余红包金额/剩余了豆个数
     *
     * @return surplus_money - 剩余红包金额/剩余了豆个数
     */
    public BigDecimal getSurplusMoney() {
        return surplusMoney;
    }

    /**
     * 设置剩余红包金额/剩余了豆个数
     *
     * @param surplusMoney 剩余红包金额/剩余了豆个数
     */
    public void setSurplusMoney(BigDecimal surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    /**
     * 获取红包个数
     *
     * @return count - 红包个数
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置红包个数
     *
     * @param count 红包个数
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取剩余红包个数
     *
     * @return surplus_count - 剩余红包个数
     */
    public Integer getSurplusCount() {
        return surplusCount;
    }

    /**
     * 设置剩余红包个数
     *
     * @param surplusCount 剩余红包个数
     */
    public void setSurplusCount(Integer surplusCount) {
        this.surplusCount = surplusCount;
    }

    /**
     * 获取红包类型   1. 余额，  2.豆
     *
     * @return type - 红包类型   1. 余额，  2.豆
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置红包类型   1. 余额，  2.豆
     *
     * @param type 红包类型   1. 余额，  2.豆
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取红包类型  1.单聊红包   2.群红包  3.消费红包(分享奖励商品)  4.分享红包(分享奖励商品)
     *
     * @return red_packet_type - 红包类型  1.单聊红包   2.群红包  3.消费红包(分享奖励商品)  4.分享红包(分享奖励商品)
     */
    public Byte getRedPacketType() {
        return redPacketType;
    }

    /**
     * 设置红包类型  1.单聊红包   2.群红包  3.消费红包(分享奖励商品)  4.分享红包(分享奖励商品)
     *
     * @param redPacketType 红包类型  1.单聊红包   2.群红包  3.消费红包(分享奖励商品)  4.分享红包(分享奖励商品)
     */
    public void setRedPacketType(Byte redPacketType) {
        this.redPacketType = redPacketType;
    }

    /**
     * 获取红包结果  1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) 6.已领取(分享奖励商品)
     *
     * @return result - 红包结果  1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) 6.已领取(分享奖励商品)
     */
    public Byte getResult() {
        return result;
    }

    /**
     * 设置红包结果  1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) 6.已领取(分享奖励商品)
     *
     * @param result 红包结果  1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) 6.已领取(分享奖励商品)
     */
    public void setResult(Byte result) {
        this.result = result;
    }

    /**
     * 获取红包发送时间
     *
     * @return create_time - 红包发送时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置红包发送时间
     *
     * @param createTime 红包发送时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取红包抢完时间
     *
     * @return end_time - 红包抢完时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置红包抢完时间
     *
     * @param endTime 红包抢完时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取红包过期时间
     *
     * @return timed_time - 红包过期时间
     */
    public Date getTimedTime() {
        return timedTime;
    }

    /**
     * 设置红包过期时间
     *
     * @param timedTime 红包过期时间
     */
    public void setTimedTime(Date timedTime) {
        this.timedTime = timedTime;
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

    /**
     * 获取分享-订单表的主键
     *
     * @return order_extend_id - 分享-订单表的主键
     */
    public String getOrderExtendId() {
        return orderExtendId;
    }

    /**
     * 设置分享-订单表的主键
     *
     * @param orderExtendId 分享-订单表的主键
     */
    public void setOrderExtendId(String orderExtendId) {
        this.orderExtendId = orderExtendId == null ? null : orderExtendId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", money=").append(money);
        sb.append(", surplusMoney=").append(surplusMoney);
        sb.append(", count=").append(count);
        sb.append(", surplusCount=").append(surplusCount);
        sb.append(", type=").append(type);
        sb.append(", redPacketType=").append(redPacketType);
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", timedTime=").append(timedTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", orderExtendId=").append(orderExtendId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}