package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_gold_adviser")
public class SlGoldAdviser implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 客服用户ID（聊天用ID）
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 客服真实姓名
     */
    private String name;

    /**
     * 首页封面职位描述
     */
    private String identity;

    /**
     * 顾问首页封面图片
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 昵称，例如：金牌顾问李白
     */
    private String nickname;

    /**
     * 顾问详细介绍
     */
    private String description;

    /**
     * 顾问详情页图片
     */
    @Column(name = "detail_url")
    private String detailUrl;

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
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取客服用户ID（聊天用ID）
     *
     * @return user_id - 客服用户ID（聊天用ID）
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置客服用户ID（聊天用ID）
     *
     * @param userId 客服用户ID（聊天用ID）
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取客服真实姓名
     *
     * @return name - 客服真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客服真实姓名
     *
     * @param name 客服真实姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取首页封面职位描述
     *
     * @return identity - 首页封面职位描述
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * 设置首页封面职位描述
     *
     * @param identity 首页封面职位描述
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * 获取顾问首页封面图片
     *
     * @return cover_url - 顾问首页封面图片
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * 设置顾问首页封面图片
     *
     * @param coverUrl 顾问首页封面图片
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl == null ? null : coverUrl.trim();
    }

    /**
     * 获取昵称，例如：金牌顾问李白
     *
     * @return nickname - 昵称，例如：金牌顾问李白
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称，例如：金牌顾问李白
     *
     * @param nickname 昵称，例如：金牌顾问李白
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取顾问详细介绍
     *
     * @return description - 顾问详细介绍
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置顾问详细介绍
     *
     * @param description 顾问详细介绍
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取顾问详情页图片
     *
     * @return detail_url - 顾问详情页图片
     */
    public String getDetailUrl() {
        return detailUrl;
    }

    /**
     * 设置顾问详情页图片
     *
     * @param detailUrl 顾问详情页图片
     */
    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
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
        sb.append(", name=").append(name);
        sb.append(", identity=").append(identity);
        sb.append(", coverUrl=").append(coverUrl);
        sb.append(", nickname=").append(nickname);
        sb.append(", description=").append(description);
        sb.append(", detailUrl=").append(detailUrl);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}