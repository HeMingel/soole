package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_admin_message")
public class SlAdminMessage implements Serializable {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 消息标题
     */
    @Column(name = "am_title")
    private String amTitle;

    /**
     * 针对用户类型1代理商 2店铺
     */
    @Column(name = "am_user_type")
    private Boolean amUserType;

    /**
     * 消息类型 1客服消息 2平台公告  3活动公告  4系统通知
     */
    @Column(name = "am_msg_type")
    private Byte amMsgType;

    /**
     * 消息发布时间
     */
    @Column(name = "am_time")
    private String amTime;

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

    /**
     * 消息内容
     */
    @Column(name = "am_content")
    private String amContent;

    private static final long serialVersionUID = 1L;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取消息标题
     *
     * @return am_title - 消息标题
     */
    public String getAmTitle() {
        return amTitle;
    }

    /**
     * 设置消息标题
     *
     * @param amTitle 消息标题
     */
    public void setAmTitle(String amTitle) {
        this.amTitle = amTitle == null ? null : amTitle.trim();
    }

    /**
     * 获取针对用户类型1代理商 2店铺
     *
     * @return am_user_type - 针对用户类型1代理商 2店铺
     */
    public Boolean getAmUserType() {
        return amUserType;
    }

    /**
     * 设置针对用户类型1代理商 2店铺
     *
     * @param amUserType 针对用户类型1代理商 2店铺
     */
    public void setAmUserType(Boolean amUserType) {
        this.amUserType = amUserType;
    }

    /**
     * 获取消息类型 1客服消息 2平台公告  3活动公告  4系统通知
     *
     * @return am_msg_type - 消息类型 1客服消息 2平台公告  3活动公告  4系统通知
     */
    public Byte getAmMsgType() {
        return amMsgType;
    }

    /**
     * 设置消息类型 1客服消息 2平台公告  3活动公告  4系统通知
     *
     * @param amMsgType 消息类型 1客服消息 2平台公告  3活动公告  4系统通知
     */
    public void setAmMsgType(Byte amMsgType) {
        this.amMsgType = amMsgType;
    }

    /**
     * 获取消息发布时间
     *
     * @return am_time - 消息发布时间
     */
    public String getAmTime() {
        return amTime;
    }

    /**
     * 设置消息发布时间
     *
     * @param amTime 消息发布时间
     */
    public void setAmTime(String amTime) {
        this.amTime = amTime == null ? null : amTime.trim();
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

    /**
     * 获取消息内容
     *
     * @return am_content - 消息内容
     */
    public String getAmContent() {
        return amContent;
    }

    /**
     * 设置消息内容
     *
     * @param amContent 消息内容
     */
    public void setAmContent(String amContent) {
        this.amContent = amContent == null ? null : amContent.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", amTitle=").append(amTitle);
        sb.append(", amUserType=").append(amUserType);
        sb.append(", amMsgType=").append(amMsgType);
        sb.append(", amTime=").append(amTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", amContent=").append(amContent);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}