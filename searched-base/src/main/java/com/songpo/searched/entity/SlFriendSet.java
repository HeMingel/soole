package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_friend_set")
public class SlFriendSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "user_id")
    private String userId;
    /**
     * 1. 直接加好友（好友可直接添加自己为好友）   2. 自行点击同意加好友（需要自己同意才能被被人添加为好友）
     */
    @Column(name = "add_status")
    private Boolean addStatus;
    /**
     * 融云token
     */
    @Column(name = "ry_token")
    private String ryToken;

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
     * 获取1. 直接加好友（好友可直接添加自己为好友）   2. 自行点击同意加好友（需要自己同意才能被被人添加为好友）
     *
     * @return add_status - 1. 直接加好友（好友可直接添加自己为好友）   2. 自行点击同意加好友（需要自己同意才能被被人添加为好友）
     */
    public Boolean getAddStatus() {
        return addStatus;
    }

    /**
     * 设置1. 直接加好友（好友可直接添加自己为好友）   2. 自行点击同意加好友（需要自己同意才能被被人添加为好友）
     *
     * @param addStatus 1. 直接加好友（好友可直接添加自己为好友）   2. 自行点击同意加好友（需要自己同意才能被被人添加为好友）
     */
    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    /**
     * 获取融云token
     *
     * @return ry_token - 融云token
     */
    public String getRyToken() {
        return ryToken;
    }

    /**
     * 设置融云token
     *
     * @param ryToken 融云token
     */
    public void setRyToken(String ryToken) {
        this.ryToken = ryToken == null ? null : ryToken.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", addStatus=").append(addStatus);
        sb.append(", ryToken=").append(ryToken);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}