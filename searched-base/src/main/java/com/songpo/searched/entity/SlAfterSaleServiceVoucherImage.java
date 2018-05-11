package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_after_sale_service_voucher_image")
public class SlAfterSaleServiceVoucherImage implements Serializable {
    /**
     * 售后服务凭证图片唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 售后服务表唯一标识符
     */
    @Column(name = "after_sales_service_id")
    private String afterSalesServiceId;

    /**
     * 图片地址
     */
    @Column(name = "image_url")
    private String imageUrl;

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
     * 获取售后服务凭证图片唯一标识符
     *
     * @return id - 售后服务凭证图片唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置售后服务凭证图片唯一标识符
     *
     * @param id 售后服务凭证图片唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取售后服务表唯一标识符
     *
     * @return after_sales_service_id - 售后服务表唯一标识符
     */
    public String getAfterSalesServiceId() {
        return afterSalesServiceId;
    }

    /**
     * 设置售后服务表唯一标识符
     *
     * @param afterSalesServiceId 售后服务表唯一标识符
     */
    public void setAfterSalesServiceId(String afterSalesServiceId) {
        this.afterSalesServiceId = afterSalesServiceId == null ? null : afterSalesServiceId.trim();
    }

    /**
     * 获取图片地址
     *
     * @return image_url - 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片地址
     *
     * @param imageUrl 图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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
        sb.append(", afterSalesServiceId=").append(afterSalesServiceId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}