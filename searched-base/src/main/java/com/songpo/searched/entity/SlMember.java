package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_member")
public class SlMember implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户唯一标识符
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 经验值
     */
    private Integer exp;

    /**
     * 是否开启消息推送  1.开启  2.不开启 
     */
    @Column(name = "is_push")
    private Boolean isPush;

    /**
     * 创建人
     */
    private String creater;

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
     * 获取用户唯一标识符
     *
     * @return user_id - 用户唯一标识符
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户唯一标识符
     *
     * @param userId 用户唯一标识符
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取等级
     *
     * @return level - 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取经验值
     *
     * @return exp - 经验值
     */
    public Integer getExp() {
        return exp;
    }

    /**
     * 设置经验值
     *
     * @param exp 经验值
     */
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 获取是否开启消息推送  1.开启  2.不开启 
     *
     * @return is_push - 是否开启消息推送  1.开启  2.不开启 
     */
    public Boolean getIsPush() {
        return isPush;
    }

    /**
     * 设置是否开启消息推送  1.开启  2.不开启 
     *
     * @param isPush 是否开启消息推送  1.开启  2.不开启 
     */
    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    /**
     * 获取创建人
     *
     * @return creater - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
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
        sb.append(", userId=").append(userId);
        sb.append(", level=").append(level);
        sb.append(", exp=").append(exp);
        sb.append(", isPush=").append(isPush);
        sb.append(", creater=").append(creater);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}