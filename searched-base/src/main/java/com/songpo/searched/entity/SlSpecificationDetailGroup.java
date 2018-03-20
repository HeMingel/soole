package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_specification_detail_group")
public class SlSpecificationDetailGroup implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 规格明细唯一标识符
     */
    @Column(name = "specification_detail_id")
    private String specificationDetailId;

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
        sb.append(", specificationDetailId=").append(specificationDetailId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}