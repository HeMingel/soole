package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product_spec_value")
public class SlProductSpecValue implements Serializable {
    /**
     * 商品属性值ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品属性ID
     */
    @Column(name = "spec_id")
    private Integer specId;

    /**
     * 商品属性值名称
     */
    @Column(name = "spec_value_name")
    private String specValueName;

    /**
     * 商品属性值数据
     */
    @Column(name = "spec_value_data")
    private String specValueData;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品属性值ID
     *
     * @return id - 商品属性值ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品属性值ID
     *
     * @param id 商品属性值ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品属性ID
     *
     * @return spec_id - 商品属性ID
     */
    public Integer getSpecId() {
        return specId;
    }

    /**
     * 设置商品属性ID
     *
     * @param specId 商品属性ID
     */
    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    /**
     * 获取商品属性值名称
     *
     * @return spec_value_name - 商品属性值名称
     */
    public String getSpecValueName() {
        return specValueName;
    }

    /**
     * 设置商品属性值名称
     *
     * @param specValueName 商品属性值名称
     */
    public void setSpecValueName(String specValueName) {
        this.specValueName = specValueName == null ? null : specValueName.trim();
    }

    /**
     * 获取商品属性值数据
     *
     * @return spec_value_data - 商品属性值数据
     */
    public String getSpecValueData() {
        return specValueData;
    }

    /**
     * 设置商品属性值数据
     *
     * @param specValueData 商品属性值数据
     */
    public void setSpecValueData(String specValueData) {
        this.specValueData = specValueData == null ? null : specValueData.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", specId=").append(specId);
        sb.append(", specValueName=").append(specValueName);
        sb.append(", specValueData=").append(specValueData);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}