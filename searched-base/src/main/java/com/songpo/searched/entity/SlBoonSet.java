package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_boon_set")
public class SlBoonSet implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 福利id
     */
    @Column(name = "boon_id")
    private String boonId;

    /**
     * 第三方分享展示图片
     */
    @Column(name = "share_image_url")
    private String shareImageUrl;

    /**
     * 第三方分享展示标题
     */
    @Column(name = "share_title")
    private String shareTitle;

    /**
     * 第三方分享展示内容
     */
    @Column(name = "share_content")
    private String shareContent;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取福利id
     *
     * @return boon_id - 福利id
     */
    public String getBoonId() {
        return boonId;
    }

    /**
     * 设置福利id
     *
     * @param boonId 福利id
     */
    public void setBoonId(String boonId) {
        this.boonId = boonId == null ? null : boonId.trim();
    }

    /**
     * 获取第三方分享展示图片
     *
     * @return share_image_url - 第三方分享展示图片
     */
    public String getShareImageUrl() {
        return shareImageUrl;
    }

    /**
     * 设置第三方分享展示图片
     *
     * @param shareImageUrl 第三方分享展示图片
     */
    public void setShareImageUrl(String shareImageUrl) {
        this.shareImageUrl = shareImageUrl == null ? null : shareImageUrl.trim();
    }

    /**
     * 获取第三方分享展示标题
     *
     * @return share_title - 第三方分享展示标题
     */
    public String getShareTitle() {
        return shareTitle;
    }

    /**
     * 设置第三方分享展示标题
     *
     * @param shareTitle 第三方分享展示标题
     */
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle == null ? null : shareTitle.trim();
    }

    /**
     * 获取第三方分享展示内容
     *
     * @return share_content - 第三方分享展示内容
     */
    public String getShareContent() {
        return shareContent;
    }

    /**
     * 设置第三方分享展示内容
     *
     * @param shareContent 第三方分享展示内容
     */
    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_time - 最后更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateTime 最后更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", boonId=").append(boonId);
        sb.append(", shareImageUrl=").append(shareImageUrl);
        sb.append(", shareTitle=").append(shareTitle);
        sb.append(", shareContent=").append(shareContent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}