package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_action_navigation")
public class SlActionNavigation implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 元素文本
     */
    private String title;

    /**
     * 元素描述
     */
    private String description;

    /**
     * 元素图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 动作页面_android
     */
    @Column(name = "page_android")
    private String pageAndroid;

    /**
     * 动作页面_ios
     */
    @Column(name = "page_ios")
    private String pageIos;

    /**
     * 导航类型唯一标识符
     */
    @Column(name = "type_id")
    private String typeId;

    /**
     * 序号，用于区分不同的元素，比如从上至下的顺序，每个元素一个不同的序列号
     */
    @Column(name = "serial_number")
    private Integer serialNumber;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modification_time")
    private String modificationTime;

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
     * 获取元素文本
     *
     * @return title - 元素文本
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置元素文本
     *
     * @param title 元素文本
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取元素描述
     *
     * @return description - 元素描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置元素描述
     *
     * @param description 元素描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取元素图片
     *
     * @return image_url - 元素图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置元素图片
     *
     * @param imageUrl 元素图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取链接地址
     *
     * @return url - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取动作页面_android
     *
     * @return page_android - 动作页面_android
     */
    public String getPageAndroid() {
        return pageAndroid;
    }

    /**
     * 设置动作页面_android
     *
     * @param pageAndroid 动作页面_android
     */
    public void setPageAndroid(String pageAndroid) {
        this.pageAndroid = pageAndroid == null ? null : pageAndroid.trim();
    }

    /**
     * 获取动作页面_ios
     *
     * @return page_ios - 动作页面_ios
     */
    public String getPageIos() {
        return pageIos;
    }

    /**
     * 设置动作页面_ios
     *
     * @param pageIos 动作页面_ios
     */
    public void setPageIos(String pageIos) {
        this.pageIos = pageIos == null ? null : pageIos.trim();
    }

    /**
     * 获取导航类型唯一标识符
     *
     * @return type_id - 导航类型唯一标识符
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * 设置导航类型唯一标识符
     *
     * @param typeId 导航类型唯一标识符
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    /**
     * 获取序号，用于区分不同的元素，比如从上至下的顺序，每个元素一个不同的序列号
     *
     * @return serial_number - 序号，用于区分不同的元素，比如从上至下的顺序，每个元素一个不同的序列号
     */
    public Integer getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置序号，用于区分不同的元素，比如从上至下的顺序，每个元素一个不同的序列号
     *
     * @param serialNumber 序号，用于区分不同的元素，比如从上至下的顺序，每个元素一个不同的序列号
     */
    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modification_time - 修改时间
     */
    public String getModificationTime() {
        return modificationTime;
    }

    /**
     * 设置修改时间
     *
     * @param modificationTime 修改时间
     */
    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime == null ? null : modificationTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", url=").append(url);
        sb.append(", pageAndroid=").append(pageAndroid);
        sb.append(", pageIos=").append(pageIos);
        sb.append(", typeId=").append(typeId);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}