package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_product_sale_mode_order_count")
public class SlProductSaleModeOrderCount implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 店铺唯一标识符
     */
    @Column(name = "shop_id")
    private String shopId;
    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;
    /**
     * 销售模式唯一标识符
     */
    @Column(name = "sales_mode_id")
    private String salesModeId;
    /**
     * 销售数量
     */
    private Integer count;

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
     * 获取店铺唯一标识符
     *
     * @return shop_id - 店铺唯一标识符
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺唯一标识符
     *
     * @param shopId 店铺唯一标识符
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
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
     * 获取销售模式唯一标识符
     *
     * @return sales_mode_id - 销售模式唯一标识符
     */
    public String getSalesModeId() {
        return salesModeId;
    }

    /**
     * 设置销售模式唯一标识符
     *
     * @param salesModeId 销售模式唯一标识符
     */
    public void setSalesModeId(String salesModeId) {
        this.salesModeId = salesModeId == null ? null : salesModeId.trim();
    }

    /**
     * 获取销售数量
     *
     * @return count - 销售数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置销售数量
     *
     * @param count 销售数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", productId=").append(productId);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", count=").append(count);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}