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
     * 动作指令_android
     */
    @Column(name = "cmd_android")
    private String cmdAndroid;

    /**
     * 动作指令_ios
     */
    @Column(name = "cmd_ios")
    private String cmdIos;

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
     * 动作指令参数
     */
    @Column(name = "cmd_parameter")
    private String cmdParameter;

    /**
     * 动作类型，1:APP页面 2:URL地址
     */
    @Column(name = "action_type")
    private Integer actionType;

    /**
     * 排序字段（后台列表排序使用）
     */
    @Column(name = "action_sort")
    private Integer actionSort;

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
     * 获取动作指令_android
     *
     * @return cmd_android - 动作指令_android
     */
    public String getCmdAndroid() {
        return cmdAndroid;
    }

    /**
     * 设置动作指令_android
     *
     * @param cmdAndroid 动作指令_android
     */
    public void setCmdAndroid(String cmdAndroid) {
        this.cmdAndroid = cmdAndroid == null ? null : cmdAndroid.trim();
    }

    /**
     * 获取动作指令_ios
     *
     * @return cmd_ios - 动作指令_ios
     */
    public String getCmdIos() {
        return cmdIos;
    }

    /**
     * 设置动作指令_ios
     *
     * @param cmdIos 动作指令_ios
     */
    public void setCmdIos(String cmdIos) {
        this.cmdIos = cmdIos == null ? null : cmdIos.trim();
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
     * 获取动作指令参数
     *
     * @return cmd_parameter - 动作指令参数
     */
    public String getCmdParameter() {
        return cmdParameter;
    }

    /**
     * 设置动作指令参数
     *
     * @param cmdParameter 动作指令参数
     */
    public void setCmdParameter(String cmdParameter) {
        this.cmdParameter = cmdParameter == null ? null : cmdParameter.trim();
    }

    /**
     * 获取动作类型，1:APP页面 2:URL地址
     *
     * @return action_type - 动作类型，1:APP页面 2:URL地址
     */
    public Integer getActionType() {
        return actionType;
    }

    /**
     * 设置动作类型，1:APP页面 2:URL地址
     *
     * @param actionType 动作类型，1:APP页面 2:URL地址
     */
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    /**
     * 获取排序字段（后台列表排序使用）
     *
     * @return action_sort - 排序字段（后台列表排序使用）
     */
    public Integer getActionSort() {
        return actionSort;
    }

    /**
     * 设置排序字段（后台列表排序使用）
     *
     * @param actionSort 排序字段（后台列表排序使用）
     */
    public void setActionSort(Integer actionSort) {
        this.actionSort = actionSort;
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
        sb.append(", cmdAndroid=").append(cmdAndroid);
        sb.append(", cmdIos=").append(cmdIos);
        sb.append(", typeId=").append(typeId);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", size=").append(size);
        sb.append(", position=").append(position);
        sb.append(", cmdParameter=").append(cmdParameter);
        sb.append(", actionType=").append(actionType);
        sb.append(", actionSort=").append(actionSort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}