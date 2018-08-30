package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_product_category_block")
public class SlProductCategoryBlock implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 实例id
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 分类别名
     */
    @Column(name = "category_alias")
    private String categoryAlias;

    /**
     * 颜色
     */
    private String color;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分类图片
     */
    @Column(name = "category_image_url")
    private String categoryImageUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Integer modifyTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取实例id
     *
     * @return shop_id - 实例id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置实例id
     *
     * @param shopId 实例id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取分类名称
     *
     * @return category_name - 分类名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置分类名称
     *
     * @param categoryName 分类名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * 获取分类id
     *
     * @return category_id - 分类id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类id
     *
     * @param categoryId 分类id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取分类别名
     *
     * @return category_alias - 分类别名
     */
    public String getCategoryAlias() {
        return categoryAlias;
    }

    /**
     * 设置分类别名
     *
     * @param categoryAlias 分类别名
     */
    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias == null ? null : categoryAlias.trim();
    }

    /**
     * 获取颜色
     *
     * @return color - 颜色
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置颜色
     *
     * @param color 颜色
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
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
     * 获取分类图片
     *
     * @return category_image_url - 分类图片
     */
    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    /**
     * 设置分类图片
     *
     * @param categoryImageUrl 分类图片
     */
    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl == null ? null : categoryImageUrl.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Integer getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", categoryAlias=").append(categoryAlias);
        sb.append(", color=").append(color);
        sb.append(", sort=").append(sort);
        sb.append(", categoryImageUrl=").append(categoryImageUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}