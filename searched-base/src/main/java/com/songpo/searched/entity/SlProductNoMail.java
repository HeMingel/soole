package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "sl_product_no_mail")
public class SlProductNoMail implements Serializable {
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
     * 不包邮地区名称
     */
    @Column(name = "no_ship_area")
    private String noShipArea;

    /**
     * 快递方式
     */
    private String express;

    /**
     * 不包邮地区运费
     */
    @Column(name = "areaMoney")
    private BigDecimal areamoney;

    /**
     * 不包邮地区每次超出运费需要加的钱
     */
    @Column(name = "add_express_money")
    private BigDecimal addExpressMoney;

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
     * 获取不包邮地区名称
     *
     * @return no_ship_area - 不包邮地区名称
     */
    public String getNoShipArea() {
        return noShipArea;
    }

    /**
     * 设置不包邮地区名称
     *
     * @param noShipArea 不包邮地区名称
     */
    public void setNoShipArea(String noShipArea) {
        this.noShipArea = noShipArea == null ? null : noShipArea.trim();
    }

    /**
     * 获取快递方式
     *
     * @return express - 快递方式
     */
    public String getExpress() {
        return express;
    }

    /**
     * 设置快递方式
     *
     * @param express 快递方式
     */
    public void setExpress(String express) {
        this.express = express == null ? null : express.trim();
    }

    /**
     * 获取不包邮地区运费
     *
     * @return areaMoney - 不包邮地区运费
     */
    public BigDecimal getAreamoney() {
        return areamoney;
    }

    /**
     * 设置不包邮地区运费
     *
     * @param areamoney 不包邮地区运费
     */
    public void setAreamoney(BigDecimal areamoney) {
        this.areamoney = areamoney;
    }

    /**
     * 获取不包邮地区每次超出运费需要加的钱
     *
     * @return add_express_money - 不包邮地区每次超出运费需要加的钱
     */
    public BigDecimal getAddExpressMoney() {
        return addExpressMoney;
    }

    /**
     * 设置不包邮地区每次超出运费需要加的钱
     *
     * @param addExpressMoney 不包邮地区每次超出运费需要加的钱
     */
    public void setAddExpressMoney(BigDecimal addExpressMoney) {
        this.addExpressMoney = addExpressMoney;
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
        sb.append(", productId=").append(productId);
        sb.append(", noShipArea=").append(noShipArea);
        sb.append(", express=").append(express);
        sb.append(", areamoney=").append(areamoney);
        sb.append(", addExpressMoney=").append(addExpressMoney);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}