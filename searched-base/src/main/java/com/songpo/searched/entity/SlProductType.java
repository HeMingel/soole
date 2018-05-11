package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product_type")
public class SlProductType implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级类别唯一标识符
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 商品分类图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 分类排序字段（越大越靠前）
     */
    @Column(name = "c_order")
    private Integer cOrder;

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
     * 获取上级类别唯一标识符
     *
     * @return parent_id - 上级类别唯一标识符
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级类别唯一标识符
     *
     * @param parentId 上级类别唯一标识符
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * 获取商品分类图片
     *
     * @return image_url - 商品分类图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置商品分类图片
     *
     * @param imageUrl 商品分类图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取分类排序字段（越大越靠前）
     *
     * @return c_order - 分类排序字段（越大越靠前）
     */
    public Integer getcOrder() {
        return cOrder;
    }

    /**
     * 设置分类排序字段（越大越靠前）
     *
     * @param cOrder 分类排序字段（越大越靠前）
     */
    public void setcOrder(Integer cOrder) {
        this.cOrder = cOrder;
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
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", cOrder=").append(cOrder);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}