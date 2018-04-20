package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_after_sale_service_voucherr_image")
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", afterSalesServiceId=").append(afterSalesServiceId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}