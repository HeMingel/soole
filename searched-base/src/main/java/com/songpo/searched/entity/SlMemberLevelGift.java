package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_member_level_gift")
public class SlMemberLevelGift implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 礼包对应等级id
     */
    @Column(name = "level_id")
    private String levelId;

    /**
     * 礼包姓名
     */
    private String name;

    /**
     * 礼包图标
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 礼包描述
     */
    private String descriptional;

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
     * 获取礼包对应等级id
     *
     * @return level_id - 礼包对应等级id
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * 设置礼包对应等级id
     *
     * @param levelId 礼包对应等级id
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    /**
     * 获取礼包姓名
     *
     * @return name - 礼包姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置礼包姓名
     *
     * @param name 礼包姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取礼包图标
     *
     * @return image_url - 礼包图标
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置礼包图标
     *
     * @param imageUrl 礼包图标
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取礼包描述
     *
     * @return descriptional - 礼包描述
     */
    public String getDescriptional() {
        return descriptional;
    }

    /**
     * 设置礼包描述
     *
     * @param descriptional 礼包描述
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
        sb.append(", levelId=").append(levelId);
        sb.append(", name=").append(name);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", descriptional=").append(descriptional);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}