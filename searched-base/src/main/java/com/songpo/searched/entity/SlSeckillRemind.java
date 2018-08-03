package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_seckill_remind")
public class SlSeckillRemind implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品ID(int自增类型)
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 商品ID(varchar类型)
     */
    @Column(name = "product_old_id")
    private String productOldId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 提醒时间
     */
    @Column(name = "remind_time")
    private Date remindTime;

    /**
     * 是否可用 1可用  0 不可用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 最后更新时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品ID(int自增类型)
     *
     * @return product_id - 商品ID(int自增类型)
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品ID(int自增类型)
     *
     * @param productId 商品ID(int自增类型)
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取商品ID(varchar类型)
     *
     * @return product_old_id - 商品ID(varchar类型)
     */
    public String getProductOldId() {
        return productOldId;
    }

    /**
     * 设置商品ID(varchar类型)
     *
     * @param productOldId 商品ID(varchar类型)
     */
    public void setProductOldId(String productOldId) {
        this.productOldId = productOldId == null ? null : productOldId.trim();
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取提醒时间
     *
     * @return remind_time - 提醒时间
     */
    public Date getRemindTime() {
        return remindTime;
    }

    /**
     * 设置提醒时间
     *
     * @param remindTime 提醒时间
     */
    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    /**
     * 获取是否可用 1可用  0 不可用
     *
     * @return enable - 是否可用 1可用  0 不可用
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否可用 1可用  0 不可用
     *
     * @param enable 是否可用 1可用  0 不可用
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_at - 最后更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateAt 最后更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", productOldId=").append(productOldId);
        sb.append(", userId=").append(userId);
        sb.append(", remindTime=").append(remindTime);
        sb.append(", enable=").append(enable);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}