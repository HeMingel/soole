package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_member_growth_task")
public class SlMemberGrowthTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 成长任务标题
     */
    private String title;

    /**
     * 成长任务内容
     */
    private String content;

    /**
     * 成长任务图标
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 成长任务描述
     */
    private String descriptional;

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
     * 获取成长任务标题
     *
     * @return title - 成长任务标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置成长任务标题
     *
     * @param title 成长任务标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取成长任务内容
     *
     * @return content - 成长任务内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置成长任务内容
     *
     * @param content 成长任务内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取成长任务图标
     *
     * @return image_url - 成长任务图标
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置成长任务图标
     *
     * @param imageUrl 成长任务图标
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取成长任务描述
     *
     * @return descriptional - 成长任务描述
     */
    public String getDescriptional() {
        return descriptional;
    }

    /**
     * 设置成长任务描述
     *
     * @param descriptional 成长任务描述
     */
    public void setDescriptional(String descriptional) {
        this.descriptional = descriptional == null ? null : descriptional.trim();
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
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", descriptional=").append(descriptional);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}