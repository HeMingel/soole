package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product_spec")
public class SlProductSpec implements Serializable {
    /**
     * 属性id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 属性名称
     */
    @Column(name = "spec_name")
    private String specName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 展示方式 1 文字 2 颜色 3 图片
     */
    @Column(name = "show_type")
    private Integer showType;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否是平台规格 1是  2不是
     */
    @Column(name = "is_platform")
    private Integer isPlatform;

    /**
     * 属性说明
     */
    @Column(name = "spec_des")
    private String specDes;

    private static final long serialVersionUID = 1L;

    /**
     * 获取属性id
     *
     * @return id - 属性id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置属性id
     *
     * @param id 属性id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取属性名称
     *
     * @return spec_name - 属性名称
     */
    public String getSpecName() {
        return specName;
    }

    /**
     * 设置属性名称
     *
     * @param specName 属性名称
     */
    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
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
     * 获取展示方式 1 文字 2 颜色 3 图片
     *
     * @return show_type - 展示方式 1 文字 2 颜色 3 图片
     */
    public Integer getShowType() {
        return showType;
    }

    /**
     * 设置展示方式 1 文字 2 颜色 3 图片
     *
     * @param showType 展示方式 1 文字 2 颜色 3 图片
     */
    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
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

    /**
     * 获取是否是平台规格 1是  2不是
     *
     * @return is_platform - 是否是平台规格 1是  2不是
     */
    public Integer getIsPlatform() {
        return isPlatform;
    }

    /**
     * 设置是否是平台规格 1是  2不是
     *
     * @param isPlatform 是否是平台规格 1是  2不是
     */
    public void setIsPlatform(Integer isPlatform) {
        this.isPlatform = isPlatform;
    }

    /**
     * 获取属性说明
     *
     * @return spec_des - 属性说明
     */
    public String getSpecDes() {
        return specDes;
    }

    /**
     * 设置属性说明
     *
     * @param specDes 属性说明
     */
    public void setSpecDes(String specDes) {
        this.specDes = specDes == null ? null : specDes.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", specName=").append(specName);
        sb.append(", sort=").append(sort);
        sb.append(", showType=").append(showType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isPlatform=").append(isPlatform);
        sb.append(", specDes=").append(specDes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}