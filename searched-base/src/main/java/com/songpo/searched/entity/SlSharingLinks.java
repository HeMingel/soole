package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_sharing_links")
public class SlSharingLinks implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 分享人ID
     */
    @Column(name = "share_person_id")
    private String sharePersonId;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 是否失效 1已失效 2 未失效
     */
    @Column(name = "is_failure")
    private Byte isFailure;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取分享人ID
     *
     * @return share_person_id - 分享人ID
     */
    public String getSharePersonId() {
        return sharePersonId;
    }

    /**
     * 设置分享人ID
     *
     * @param sharePersonId 分享人ID
     */
    public void setSharePersonId(String sharePersonId) {
        this.sharePersonId = sharePersonId == null ? null : sharePersonId.trim();
    }

    /**
     * 获取商品ID
     *
     * @return product_id - 商品ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     *
     * @param productId 商品ID
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取是否失效 1已失效 2 未失效
     *
     * @return is_failure - 是否失效 1已失效 2 未失效
     */
    public Byte getIsFailure() {
        return isFailure;
    }

    /**
     * 设置是否失效 1已失效 2 未失效
     *
     * @param isFailure 是否失效 1已失效 2 未失效
     */
    public void setIsFailure(Byte isFailure) {
        this.isFailure = isFailure;
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
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
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
        sb.append(", sharePersonId=").append(sharePersonId);
        sb.append(", productId=").append(productId);
        sb.append(", isFailure=").append(isFailure);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}