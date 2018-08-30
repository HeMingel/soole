package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product_category")
public class SlProductCategory implements Serializable {
    /**
     * 分类id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 商品分类简称 
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 当前分类层级
     */
    private Byte level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 商品分类图片
     */
    @Column(name = "category_image_url")
    private String categoryImageUrl;

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
     * 获取分类id
     *
     * @return id - 分类id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置分类id
     *
     * @param id 分类id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取商品分类简称 
     *
     * @return short_name - 商品分类简称 
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置商品分类简称 
     *
     * @param shortName 商品分类简称 
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * 获取父id
     *
     * @return pid - 父id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父id
     *
     * @param pid 父id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取当前分类层级
     *
     * @return level - 当前分类层级
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置当前分类层级
     *
     * @param level 当前分类层级
     */
    public void setLevel(Byte level) {
        this.level = level;
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
     * 获取商品分类图片
     *
     * @return category_image_url - 商品分类图片
     */
    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    /**
     * 设置商品分类图片
     *
     * @param categoryImageUrl 商品分类图片
     */
    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl == null ? null : categoryImageUrl.trim();
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
        sb.append(", categoryName=").append(categoryName);
        sb.append(", shortName=").append(shortName);
        sb.append(", pid=").append(pid);
        sb.append(", level=").append(level);
        sb.append(", sort=").append(sort);
        sb.append(", categoryImageUrl=").append(categoryImageUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}