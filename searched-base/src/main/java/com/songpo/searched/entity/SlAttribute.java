package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_attribute")
public class SlAttribute implements Serializable {
    /**
     * 商品属性ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 属性名称
     */
    @Column(name = "attr_name")
    private String attrName;

    /**
     * 是否使用
     */
    @Column(name = "is_use")
    private Boolean isUse;

    /**
     * 商铺id（ -1 即为平台后台设置）
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 关联规格
     */
    @Column(name = "spec_id_array")
    private String specIdArray;

    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品属性ID
     *
     * @return id - 商品属性ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品属性ID
     *
     * @param id 商品属性ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取属性名称
     *
     * @return attr_name - 属性名称
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * 设置属性名称
     *
     * @param attrName 属性名称
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    /**
     * 获取是否使用
     *
     * @return is_use - 是否使用
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置是否使用
     *
     * @param isUse 是否使用
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取商铺id（ -1 即为平台后台设置）
     *
     * @return shop_id - 商铺id（ -1 即为平台后台设置）
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置商铺id（ -1 即为平台后台设置）
     *
     * @param shopId 商铺id（ -1 即为平台后台设置）
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取关联规格
     *
     * @return spec_id_array - 关联规格
     */
    public String getSpecIdArray() {
        return specIdArray;
    }

    /**
     * 设置关联规格
     *
     * @param specIdArray 关联规格
     */
    public void setSpecIdArray(String specIdArray) {
        this.specIdArray = specIdArray == null ? null : specIdArray.trim();
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
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
        sb.append(", attrName=").append(attrName);
        sb.append(", isUse=").append(isUse);
        sb.append(", shopId=").append(shopId);
        sb.append(", specIdArray=").append(specIdArray);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}