package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_invite_friends")
public class SlInviteFriends implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 邀请人id
     */
    @Column(name = "inviter_id")
    private String inviterId;

    /**
     * 被邀请人id（一个被邀请人id只会在该表出现一次）
     */
    @Column(name = "invitee_id")
    private String inviteeId;

    /**
     * 邀请状态  1.成功
     */
    private Boolean status;

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
     * 获取邀请人id
     *
     * @return inviter_id - 邀请人id
     */
    public String getInviterId() {
        return inviterId;
    }

    /**
     * 设置邀请人id
     *
     * @param inviterId 邀请人id
     */
    public void setInviterId(String inviterId) {
        this.inviterId = inviterId == null ? null : inviterId.trim();
    }

    /**
     * 获取被邀请人id（一个被邀请人id只会在该表出现一次）
     *
     * @return invitee_id - 被邀请人id（一个被邀请人id只会在该表出现一次）
     */
    public String getInviteeId() {
        return inviteeId;
    }

    /**
     * 设置被邀请人id（一个被邀请人id只会在该表出现一次）
     *
     * @param inviteeId 被邀请人id（一个被邀请人id只会在该表出现一次）
     */
    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId == null ? null : inviteeId.trim();
    }

    /**
     * 获取邀请状态  1.成功
     *
     * @return status - 邀请状态  1.成功
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置邀请状态  1.成功
     *
     * @param status 邀请状态  1.成功
     */
    public void setStatus(Boolean status) {
        this.status = status;
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
        sb.append(", inviterId=").append(inviterId);
        sb.append(", inviteeId=").append(inviteeId);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}