package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_friend_add")
public class SlFriendAdd implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 发起加好友人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 被加好友人id
     */
    @Column(name = "friend_id")
    private String friendId;

    /**
     * 加好友结果  0.等待对方处理  1.同意  2.拒绝
     */
    private Boolean result;

    /**
     * 添加好友时备注信息
     */
    private String message;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 对方处理信息时间
     */
    @Column(name = "edit_time")
    private Date editTime;

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
     * 获取发起加好友人id
     *
     * @return user_id - 发起加好友人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置发起加好友人id
     *
     * @param userId 发起加好友人id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取被加好友人id
     *
     * @return friend_id - 被加好友人id
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * 设置被加好友人id
     *
     * @param friendId 被加好友人id
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId == null ? null : friendId.trim();
    }

    /**
     * 获取加好友结果  0.等待对方处理  1.同意  2.拒绝
     *
     * @return result - 加好友结果  0.等待对方处理  1.同意  2.拒绝
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * 设置加好友结果  0.等待对方处理  1.同意  2.拒绝
     *
     * @param result 加好友结果  0.等待对方处理  1.同意  2.拒绝
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * 获取添加好友时备注信息
     *
     * @return message - 添加好友时备注信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置添加好友时备注信息
     *
     * @param message 添加好友时备注信息
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     * 获取添加时间
     *
     * @return add_time - 添加时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置添加时间
     *
     * @param addTime 添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取对方处理信息时间
     *
     * @return edit_time - 对方处理信息时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置对方处理信息时间
     *
     * @param editTime 对方处理信息时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
        sb.append(", friendId=").append(friendId);
        sb.append(", result=").append(result);
        sb.append(", message=").append(message);
        sb.append(", addTime=").append(addTime);
        sb.append(", editTime=").append(editTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}