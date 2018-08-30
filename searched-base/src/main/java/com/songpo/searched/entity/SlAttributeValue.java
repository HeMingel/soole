package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_attribute_value")
public class SlAttributeValue implements Serializable {
    /**
     * 属性值ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 属性值名称
     */
    @Column(name = "attr_value_name")
    private String attrValueName;

    /**
     * 属性ID
     */
    @Column(name = "attr_id")
    private Integer attrId;

    /**
     * 属性对应相关数据
     */
    private String value;

    /**
     * 属性对应输入类型1.直接2.单选3.多选
     */
    private Integer type;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 是否使用
     */
    @Column(name = "is_search")
    private Boolean isSearch;

    private static final long serialVersionUID = 1L;

    /**
     * 获取属性值ID
     *
     * @return id - 属性值ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置属性值ID
     *
     * @param id 属性值ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取属性值名称
     *
     * @return attr_value_name - 属性值名称
     */
    public String getAttrValueName() {
        return attrValueName;
    }

    /**
     * 设置属性值名称
     *
     * @param attrValueName 属性值名称
     */
    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName == null ? null : attrValueName.trim();
    }

    /**
     * 获取属性ID
     *
     * @return attr_id - 属性ID
     */
    public Integer getAttrId() {
        return attrId;
    }

    /**
     * 设置属性ID
     *
     * @param attrId 属性ID
     */
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    /**
     * 获取属性对应相关数据
     *
     * @return value - 属性对应相关数据
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置属性对应相关数据
     *
     * @param value 属性对应相关数据
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * 获取属性对应输入类型1.直接2.单选3.多选
     *
     * @return type - 属性对应输入类型1.直接2.单选3.多选
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置属性对应输入类型1.直接2.单选3.多选
     *
     * @param type 属性对应输入类型1.直接2.单选3.多选
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否使用
     *
     * @return is_search - 是否使用
     */
    public Boolean getIsSearch() {
        return isSearch;
    }

    /**
     * 设置是否使用
     *
     * @param isSearch 是否使用
     */
    public void setIsSearch(Boolean isSearch) {
        this.isSearch = isSearch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", attrValueName=").append(attrValueName);
        sb.append(", attrId=").append(attrId);
        sb.append(", value=").append(value);
        sb.append(", type=").append(type);
        sb.append(", sort=").append(sort);
        sb.append(", isSearch=").append(isSearch);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}