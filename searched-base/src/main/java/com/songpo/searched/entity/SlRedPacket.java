package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_red_packet")
public class SlRedPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "user_id")
    private String userId;
    /**
     * 红包金额 若红包类型为金豆，则此处为0
     */
    private Long money;
    /**
     * 金豆数量，若红包类型为 余额，此处为0
     */
    private Integer bean;
    /**
     * 红包个数
     */
    private Integer count;
    /**
     * 红包类型   1. 余额，  2.豆
     */
    private Boolean type;
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取红包金额 若红包类型为金豆，则此处为0
     *
     * @return money - 红包金额 若红包类型为金豆，则此处为0
     */
    public Long getMoney() {
        return money;
    }

    /**
     * 设置红包金额 若红包类型为金豆，则此处为0
     *
     * @param money 红包金额 若红包类型为金豆，则此处为0
     */
    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * 获取金豆数量，若红包类型为 余额，此处为0
     *
     * @return bean - 金豆数量，若红包类型为 余额，此处为0
     */
    public Integer getBean() {
        return bean;
    }

    /**
     * 设置金豆数量，若红包类型为 余额，此处为0
     *
     * @param bean 金豆数量，若红包类型为 余额，此处为0
     */
    public void setBean(Integer bean) {
        this.bean = bean;
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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", bean=").append(bean);
        sb.append(", count=").append(count);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}