package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_district")
public class SlDistrict implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级标识
     */
    @Column(name = "parent_id")
    private Short parentId;

    /**
     * 拼音首字母
     */
    private String initial;

    /**
     * 拼音首字母集合
     */
    private String initials;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 附加说明（如：广西壮族自治区 - 壮族）
     */
    private String extra;

    /**
     * 行政级别（如：广西壮族自治区 - 自治区）
     */
    private String suffix;

    /**
     * 行政代码（基本对应身份证号码前6位）
     */
    private String code;

    /**
     * 区号
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Byte orderNum;

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
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public Short getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取上级标识
     *
     * @return parent_id - 上级标识
     */
    public Short getParentId() {
        return parentId;
    }

    /**
     * 设置上级标识
     *
     * @param parentId 上级标识
     */
    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取拼音首字母
     *
     * @return initial - 拼音首字母
     */
    public String getInitial() {
        return initial;
    }

    /**
     * 设置拼音首字母
     *
     * @param initial 拼音首字母
     */
    public void setInitial(String initial) {
        this.initial = initial == null ? null : initial.trim();
    }

    /**
     * 获取拼音首字母集合
     *
     * @return initials - 拼音首字母集合
     */
    public String getInitials() {
        return initials;
    }

    /**
     * 设置拼音首字母集合
     *
     * @param initials 拼音首字母集合
     */
    public void setInitials(String initials) {
        this.initials = initials == null ? null : initials.trim();
    }

    /**
     * 获取拼音
     *
     * @return pinyin - 拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音
     *
     * @param pinyin 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    /**
     * 获取附加说明（如：广西壮族自治区 - 壮族）
     *
     * @return extra - 附加说明（如：广西壮族自治区 - 壮族）
     */
    public String getExtra() {
        return extra;
    }

    /**
     * 设置附加说明（如：广西壮族自治区 - 壮族）
     *
     * @param extra 附加说明（如：广西壮族自治区 - 壮族）
     */
    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    /**
     * 获取行政级别（如：广西壮族自治区 - 自治区）
     *
     * @return suffix - 行政级别（如：广西壮族自治区 - 自治区）
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置行政级别（如：广西壮族自治区 - 自治区）
     *
     * @param suffix 行政级别（如：广西壮族自治区 - 自治区）
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * 获取行政代码（基本对应身份证号码前6位）
     *
     * @return code - 行政代码（基本对应身份证号码前6位）
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置行政代码（基本对应身份证号码前6位）
     *
     * @param code 行政代码（基本对应身份证号码前6位）
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取区号
     *
     * @return area_code - 区号
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置区号
     *
     * @param areaCode 区号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * 获取排序
     *
     * @return order_num - 排序
     */
    public Byte getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Byte orderNum) {
        this.orderNum = orderNum;
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
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", initial=").append(initial);
        sb.append(", initials=").append(initials);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", extra=").append(extra);
        sb.append(", suffix=").append(suffix);
        sb.append(", code=").append(code);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}