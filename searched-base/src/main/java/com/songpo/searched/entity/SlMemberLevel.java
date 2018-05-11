package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_member_level")
public class SlMemberLevel implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 等级对应的数字
     */
    @Column(name = "level_number")
    private Integer levelNumber;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 特权的 logo 的路径
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 该等级范围最大值
     */
    private Integer maximum;

    /**
     * 等级描述
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
     * 获取等级对应的数字
     *
     * @return level_number - 等级对应的数字
     */
    public Integer getLevelNumber() {
        return levelNumber;
    }

    /**
     * 设置等级对应的数字
     *
     * @param levelNumber 等级对应的数字
     */
    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * 获取等级名称
     *
     * @return name - 等级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置等级名称
     *
     * @param name 等级名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取特权的 logo 的路径
     *
     * @return image_url - 特权的 logo 的路径
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置特权的 logo 的路径
     *
     * @param imageUrl 特权的 logo 的路径
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取该等级范围最大值
     *
     * @return maximum - 该等级范围最大值
     */
    public Integer getMaximum() {
        return maximum;
    }

    /**
     * 设置该等级范围最大值
     *
     * @param maximum 该等级范围最大值
     */
    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    /**
     * 获取等级描述
     *
     * @return descriptional - 等级描述
     */
    public String getDescriptional() {
        return descriptional;
    }

    /**
     * 设置等级描述
     *
     * @param descriptional 等级描述
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
        sb.append(", levelNumber=").append(levelNumber);
        sb.append(", name=").append(name);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", maximum=").append(maximum);
        sb.append(", descriptional=").append(descriptional);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}