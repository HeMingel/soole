package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

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
     * 尺寸，1：大、2：小
     */
    private Integer size;

    /**
     * 所处位置，1：上、2：下、3：左、4：右
     */
    private Integer position;

    /**
     * JSON格式数据
     */
    private String parameter;

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
     * 获取尺寸，1：大、2：小
     *
     * @return size - 尺寸，1：大、2：小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置尺寸，1：大、2：小
     *
     * @param size 尺寸，1：大、2：小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取所处位置，1：上、2：下、3：左、4：右
     *
     * @return position - 所处位置，1：上、2：下、3：左、4：右
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 设置所处位置，1：上、2：下、3：左、4：右
     *
     * @param position 所处位置，1：上、2：下、3：左、4：右
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 获取JSON格式数据
     *
     * @return parameter - JSON格式数据
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 设置JSON格式数据
     *
     * @param parameter JSON格式数据
     */
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
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
        sb.append(", size=").append(size);
        sb.append(", position=").append(position);
        sb.append(", parameter=").append(parameter);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}