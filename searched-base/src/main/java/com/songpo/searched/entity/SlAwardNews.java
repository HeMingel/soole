package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_award_news")
public class SlAwardNews implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 补偿用户ID
     */
    @Column(name = "an_user_id")
    private String anUserId;

    /**
     * 补偿数量
     */
    @Column(name = "an_number")
    private Integer anNumber;

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
     * 获取补偿用户ID
     *
     * @return an_user_id - 补偿用户ID
     */
    public String getAnUserId() {
        return anUserId;
    }

    /**
     * 设置补偿用户ID
     *
     * @param anUserId 补偿用户ID
     */
    public void setAnUserId(String anUserId) {
        this.anUserId = anUserId == null ? null : anUserId.trim();
    }

    /**
     * 获取补偿数量
     *
     * @return an_number - 补偿数量
     */
    public Integer getAnNumber() {
        return anNumber;
    }

    /**
     * 设置补偿数量
     *
     * @param anNumber 补偿数量
     */
    public void setAnNumber(Integer anNumber) {
        this.anNumber = anNumber;
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
        sb.append(", anUserId=").append(anUserId);
        sb.append(", anNumber=").append(anNumber);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}