package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_member_level_gift")
public class SlMemberLevelGift implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 礼包对应等级的数字
     */
    @Column(name = "level_number")
    private String levelNumber;

    /**
     * 礼包姓名
     */
    private String name;

    /**
     * 等级未达到的礼包的 灰色图标
     */
    @Column(name = "image_no_url")
    private String imageNoUrl;

    /**
     * 等级未达到的礼包的 亮色图标
     */
    @Column(name = "image_ok_url")
    private String imageOkUrl;

    /**
     * 礼包描述
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
     * 获取礼包对应等级的数字
     *
     * @return level_number - 礼包对应等级的数字
     */
    public String getLevelNumber() {
        return levelNumber;
    }

    /**
     * 设置礼包对应等级的数字
     *
     * @param levelNumber 礼包对应等级的数字
     */
    public void setLevelNumber(String levelNumber) {
        this.levelNumber = levelNumber == null ? null : levelNumber.trim();
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
     * 获取等级未达到的礼包的 灰色图标
     *
     * @return image_no_url - 等级未达到的礼包的 灰色图标
     */
    public String getImageNoUrl() {
        return imageNoUrl;
    }

    /**
     * 设置等级未达到的礼包的 灰色图标
     *
     * @param imageNoUrl 等级未达到的礼包的 灰色图标
     */
    public void setImageNoUrl(String imageNoUrl) {
        this.imageNoUrl = imageNoUrl == null ? null : imageNoUrl.trim();
    }

    /**
     * 获取等级未达到的礼包的 亮色图标
     *
     * @return image_ok_url - 等级未达到的礼包的 亮色图标
     */
    public String getImageOkUrl() {
        return imageOkUrl;
    }

    /**
     * 设置等级未达到的礼包的 亮色图标
     *
     * @param imageOkUrl 等级未达到的礼包的 亮色图标
     */
    public void setImageOkUrl(String imageOkUrl) {
        this.imageOkUrl = imageOkUrl == null ? null : imageOkUrl.trim();
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
        sb.append(", imageNoUrl=").append(imageNoUrl);
        sb.append(", imageOkUrl=").append(imageOkUrl);
        sb.append(", descriptional=").append(descriptional);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}