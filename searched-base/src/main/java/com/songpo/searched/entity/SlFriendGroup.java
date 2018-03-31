package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_friend_group")
public class SlFriendGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 群主id
     */
    @Column(name = "owner_id")
    private String ownerId;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 群公告
     */
    private String notice;

    /**
     * 群二维码
     */
    @Column(name = "qr_code_image")
    private String qrCodeImage;

    /**
     * 群状态（暂未使用该字段）  1.正常状态  2.群主解散群
     */
    private Boolean status;

    /**
     * 群创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取群主id
     *
     * @return owner_id - 群主id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 设置群主id
     *
     * @param ownerId 群主id
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    /**
     * 获取群名称
     *
     * @return name - 群名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置群名称
     *
     * @param name 群名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取群头像
     *
     * @return avatar - 群头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置群头像
     *
     * @param avatar 群头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取群公告
     *
     * @return notice - 群公告
     */
    public String getNotice() {
        return notice;
    }

    /**
     * 设置群公告
     *
     * @param notice 群公告
     */
    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    /**
     * 获取群二维码
     *
     * @return qr_code_image - 群二维码
     */
    public String getQrCodeImage() {
        return qrCodeImage;
    }

    /**
     * 设置群二维码
     *
     * @param qrCodeImage 群二维码
     */
    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage == null ? null : qrCodeImage.trim();
    }

    /**
     * 获取群状态（暂未使用该字段）  1.正常状态  2.群主解散群
     *
     * @return status - 群状态（暂未使用该字段）  1.正常状态  2.群主解散群
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置群状态（暂未使用该字段）  1.正常状态  2.群主解散群
     *
     * @param status 群状态（暂未使用该字段）  1.正常状态  2.群主解散群
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取群创建时间
     *
     * @return create_time - 群创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置群创建时间
     *
     * @param createTime 群创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", name=").append(name);
        sb.append(", avatar=").append(avatar);
        sb.append(", notice=").append(notice);
        sb.append(", qrCodeImage=").append(qrCodeImage);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}