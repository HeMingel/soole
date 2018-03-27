package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_specification_detail_group")
public class SlSpecificationDetailGroup implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 序列号(组的编号)
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 规格明细唯一标识符
     */
    @Column(name = "specification_detail_id")
    private String specificationDetailId;

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
     * 获取商品唯一标识符
     *
     * @return product_id - 商品唯一标识符
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品唯一标识符
     *
     * @param productId 商品唯一标识符
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取序列号(组的编号)
     *
     * @return serial_number - 序列号(组的编号)
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置序列号(组的编号)
     *
     * @param serialNumber 序列号(组的编号)
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    /**
     * 获取规格明细唯一标识符
     *
     * @return specification_detail_id - 规格明细唯一标识符
     */
    public String getSpecificationDetailId() {
        return specificationDetailId;
    }

    /**
     * 设置规格明细唯一标识符
     *
     * @param specificationDetailId 规格明细唯一标识符
     */
    public void setSpecificationDetailId(String specificationDetailId) {
        this.specificationDetailId = specificationDetailId == null ? null : specificationDetailId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", specificationDetailId=").append(specificationDetailId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}