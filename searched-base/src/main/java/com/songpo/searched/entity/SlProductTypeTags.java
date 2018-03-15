package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_product_type_tags")
public class SlProductTypeTags implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 商品类别唯一标识符
     */
    @Column(name = "product_type_id")
    private String productTypeId;
    /**
     * 标签唯一标识符
     */
    @Column(name = "product_tag_id")
    private String productTagId;

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
     * 获取商品类别唯一标识符
     *
     * @return product_type_id - 商品类别唯一标识符
     */
    public String getProductTypeId() {
        return productTypeId;
    }

    /**
     * 设置商品类别唯一标识符
     *
     * @param productTypeId 商品类别唯一标识符
     */
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }

    /**
     * 获取标签唯一标识符
     *
     * @return product_tag_id - 标签唯一标识符
     */
    public String getProductTagId() {
        return productTagId;
    }

    /**
     * 设置标签唯一标识符
     *
     * @param productTagId 标签唯一标识符
     */
    public void setProductTagId(String productTagId) {
        this.productTagId = productTagId == null ? null : productTagId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", productTagId=").append(productTagId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}