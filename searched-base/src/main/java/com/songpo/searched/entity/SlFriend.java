package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_friend")
public class SlFriend implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 好友id
     */
    @Column(name = "friend_id")
    private String friendId;
    /**
     * 自己对好友备注
     */
    @Column(name = "friend_nickname")
    private String friendNickname;
    /**
     * 自己对好友的关系  1. 正常  2.拉黑
     */
    @Column(name = "friend_status")
    private Boolean friendStatus;
    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Date addTime;

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
     * 获取好友id
     *
     * @return friend_id - 好友id
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * 设置好友id
     *
     * @param friendId 好友id
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId == null ? null : friendId.trim();
    }

    /**
     * 获取自己对好友备注
     *
     * @return friend_nickname - 自己对好友备注
     */
    public String getFriendNickname() {
        return friendNickname;
    }

    /**
     * 设置自己对好友备注
     *
     * @param friendNickname 自己对好友备注
     */
    public void setFriendNickname(String friendNickname) {
        this.friendNickname = friendNickname == null ? null : friendNickname.trim();
    }

    /**
     * 获取自己对好友的关系  1. 正常  2.拉黑
     *
     * @return friend_status - 自己对好友的关系  1. 正常  2.拉黑
     */
    public Boolean getFriendStatus() {
        return friendStatus;
    }

    /**
     * 设置自己对好友的关系  1. 正常  2.拉黑
     *
     * @param friendStatus 自己对好友的关系  1. 正常  2.拉黑
     */
    public void setFriendStatus(Boolean friendStatus) {
        this.friendStatus = friendStatus;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", friendId=").append(friendId);
        sb.append(", friendNickname=").append(friendNickname);
        sb.append(", friendStatus=").append(friendStatus);
        sb.append(", addTime=").append(addTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}