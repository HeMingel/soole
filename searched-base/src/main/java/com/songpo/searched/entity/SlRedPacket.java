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
     * 红包金额/ 红豆金额
     */
    private BigDecimal money;

    /**
     * 剩余红包金额/剩余金豆个数
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
     * 红包结果  1.有效  2.已抢完  3.过期
     */
    private Boolean result;

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
     * 获取红包金额/ 红豆金额
     *
     * @return money - 红包金额/ 红豆金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置红包金额/ 红豆金额
     *
     * @param money 红包金额/ 红豆金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取剩余红包金额/剩余金豆个数
     *
     * @return surplus_money - 剩余红包金额/剩余金豆个数
     */
    public BigDecimal getSurplusMoney() {
        return surplusMoney;
    }

    /**
     * 设置剩余红包金额/剩余金豆个数
     *
     * @param surplusMoney 剩余红包金额/剩余金豆个数
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
     * 获取红包结果  1.有效  2.已抢完  3.过期
     *
     * @return result - 红包结果  1.有效  2.已抢完  3.过期
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * 设置红包结果  1.有效  2.已抢完  3.过期
     *
     * @param result 红包结果  1.有效  2.已抢完  3.过期
     */
    public void setResult(Boolean result) {
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
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", timedTime=").append(timedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}