package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}