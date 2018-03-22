package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_member_growth_task")
public class SlMemberGrowthTask implements Serializable {
    private static final long serialVersionUID = 1L;
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}