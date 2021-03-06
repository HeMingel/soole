package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_red_packet_detail")
public class SlRedPacketDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 红包id
     */
    @Column(name = "red_packet_id")
    private String redPacketId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 抢到的金额或者金豆数量
     */
    private BigDecimal money;

    /**
     * 红包类型  1.余额  2.金豆
     */
    private Byte type;

    /**
     * 抢红包时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取红包id
     *
     * @return red_packet_id - 红包id
     */
    public String getRedPacketId() {
        return redPacketId;
    }

    /**
     * 设置红包id
     *
     * @param redPacketId 红包id
     */
    public void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId == null ? null : redPacketId.trim();
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
     * 获取抢到的金额或者金豆数量
     *
     * @return money - 抢到的金额或者金豆数量
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置抢到的金额或者金豆数量
     *
     * @param money 抢到的金额或者金豆数量
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取红包类型  1.余额  2.金豆
     *
     * @return type - 红包类型  1.余额  2.金豆
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置红包类型  1.余额  2.金豆
     *
     * @param type 红包类型  1.余额  2.金豆
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取抢红包时间
     *
     * @return create_time - 抢红包时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置抢红包时间
     *
     * @param createTime 抢红包时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", redPacketId=").append(redPacketId);
        sb.append(", userId=").append(userId);
        sb.append(", money=").append(money);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}