package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_activity_product_repository")
public class SlActivityProductRepository implements Serializable {
    /**
     * 活动仓库唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 活动商品ID  activity_product
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 仓库唯一标识符
     */
    @Column(name = "product_repository_id")
    private String productRepositoryId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取活动仓库唯一标识符
     *
     * @return id - 活动仓库唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置活动仓库唯一标识符
     *
     * @param id 活动仓库唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取活动商品ID  activity_product
     *
     * @return serial_number - 活动商品ID  activity_product
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置活动商品ID  activity_product
     *
     * @param serialNumber 活动商品ID  activity_product
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    /**
     * 获取仓库唯一标识符
     *
     * @return product_repository_id - 仓库唯一标识符
     */
    public String getProductRepositoryId() {
        return productRepositoryId;
    }

    /**
     * 设置仓库唯一标识符
     *
     * @param productRepositoryId 仓库唯一标识符
     */
    public void setProductRepositoryId(String productRepositoryId) {
        this.productRepositoryId = productRepositoryId == null ? null : productRepositoryId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", productRepositoryId=").append(productRepositoryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}