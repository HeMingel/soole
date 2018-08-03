package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_video_copy")
public class SlVideoCopy implements Serializable {
    /**
     * 录播视频ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "vc_username")
    private Integer vcUsername;

    /**
     * 录播地址
     */
    @Column(name = "vc_url")
    private String vcUrl;

    /**
     * 视频时长
     */
    @Column(name = "vc_duration")
    private String vcDuration;

    /**
     * 录播开始时间
     */
    @Column(name = "vc_start")
    private Integer vcStart;

    /**
     * 录播结束时间
     */
    @Column(name = "vc_end")
    private Integer vcEnd;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最新更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取录播视频ID
     *
     * @return id - 录播视频ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置录播视频ID
     *
     * @param id 录播视频ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return vc_username - 用户ID
     */
    public Integer getVcUsername() {
        return vcUsername;
    }

    /**
     * 设置用户ID
     *
     * @param vcUsername 用户ID
     */
    public void setVcUsername(Integer vcUsername) {
        this.vcUsername = vcUsername;
    }

    /**
     * 获取录播地址
     *
     * @return vc_url - 录播地址
     */
    public String getVcUrl() {
        return vcUrl;
    }

    /**
     * 设置录播地址
     *
     * @param vcUrl 录播地址
     */
    public void setVcUrl(String vcUrl) {
        this.vcUrl = vcUrl == null ? null : vcUrl.trim();
    }

    /**
     * 获取视频时长
     *
     * @return vc_duration - 视频时长
     */
    public String getVcDuration() {
        return vcDuration;
    }

    /**
     * 设置视频时长
     *
     * @param vcDuration 视频时长
     */
    public void setVcDuration(String vcDuration) {
        this.vcDuration = vcDuration == null ? null : vcDuration.trim();
    }

    /**
     * 获取录播开始时间
     *
     * @return vc_start - 录播开始时间
     */
    public Integer getVcStart() {
        return vcStart;
    }

    /**
     * 设置录播开始时间
     *
     * @param vcStart 录播开始时间
     */
    public void setVcStart(Integer vcStart) {
        this.vcStart = vcStart;
    }

    /**
     * 获取录播结束时间
     *
     * @return vc_end - 录播结束时间
     */
    public Integer getVcEnd() {
        return vcEnd;
    }

    /**
     * 设置录播结束时间
     *
     * @param vcEnd 录播结束时间
     */
    public void setVcEnd(Integer vcEnd) {
        this.vcEnd = vcEnd;
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
     * 获取最新更新时间
     *
     * @return updated_at - 最新更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最新更新时间
     *
     * @param updatedAt 最新更新时间
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
        sb.append(", vcUsername=").append(vcUsername);
        sb.append(", vcUrl=").append(vcUrl);
        sb.append(", vcDuration=").append(vcDuration);
        sb.append(", vcStart=").append(vcStart);
        sb.append(", vcEnd=").append(vcEnd);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}