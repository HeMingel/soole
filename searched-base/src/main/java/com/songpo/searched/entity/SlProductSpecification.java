package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_product_specification")
public class SlProductSpecification implements Serializable {
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
     * 规格唯一标识符
     */
    @Column(name = "specification_id")
    private String specificationId;

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
     * 获取规格唯一标识符
     *
     * @return specification_id - 规格唯一标识符
     */
    public String getSpecificationId() {
        return specificationId;
    }

    /**
     * 设置规格唯一标识符
     *
     * @param specificationId 规格唯一标识符
     */
    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId == null ? null : specificationId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", specificationId=").append(specificationId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}