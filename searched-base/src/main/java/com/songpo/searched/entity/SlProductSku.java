package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product_sku")
public class SlProductSku implements Serializable {
    /**
     * 表序号
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品编号
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * SKU名称
     */
    @Column(name = "sku_name")
    private String skuName;

    /**
     * 属性和属性值 id串 attribute + attribute value 表ID分号分隔
     */
    @Column(name = "attr_value_items")
    private String attrValueItems;

    /**
     * 属性和属性值id串组合json格式
     */
    @Column(name = "attr_value_items_format")
    private String attrValueItemsFormat;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 促销价格
     */
    @Column(name = "promote_price")
    private BigDecimal promotePrice;

    /**
     * 成本价
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 如果是第一个sku编码, 可以加图片
     */
    private Integer picture;

    /**
     * 商家编码
     */
    private String code;

    /**
     * 商品二维码
     */
    @Column(name = "QRcode")
    private String qrcode;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取表序号
     *
     * @return id - 表序号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置表序号
     *
     * @param id 表序号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品编号
     *
     * @return goods_id - 商品编号
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品编号
     *
     * @param goodsId 商品编号
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取SKU名称
     *
     * @return sku_name - SKU名称
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置SKU名称
     *
     * @param skuName SKU名称
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**
     * 获取属性和属性值 id串 attribute + attribute value 表ID分号分隔
     *
     * @return attr_value_items - 属性和属性值 id串 attribute + attribute value 表ID分号分隔
     */
    public String getAttrValueItems() {
        return attrValueItems;
    }

    /**
     * 设置属性和属性值 id串 attribute + attribute value 表ID分号分隔
     *
     * @param attrValueItems 属性和属性值 id串 attribute + attribute value 表ID分号分隔
     */
    public void setAttrValueItems(String attrValueItems) {
        this.attrValueItems = attrValueItems == null ? null : attrValueItems.trim();
    }

    /**
     * 获取属性和属性值id串组合json格式
     *
     * @return attr_value_items_format - 属性和属性值id串组合json格式
     */
    public String getAttrValueItemsFormat() {
        return attrValueItemsFormat;
    }

    /**
     * 设置属性和属性值id串组合json格式
     *
     * @param attrValueItemsFormat 属性和属性值id串组合json格式
     */
    public void setAttrValueItemsFormat(String attrValueItemsFormat) {
        this.attrValueItemsFormat = attrValueItemsFormat == null ? null : attrValueItemsFormat.trim();
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取促销价格
     *
     * @return promote_price - 促销价格
     */
    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    /**
     * 设置促销价格
     *
     * @param promotePrice 促销价格
     */
    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    /**
     * 获取成本价
     *
     * @return cost_price - 成本价
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价
     *
     * @param costPrice 成本价
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取如果是第一个sku编码, 可以加图片
     *
     * @return picture - 如果是第一个sku编码, 可以加图片
     */
    public Integer getPicture() {
        return picture;
    }

    /**
     * 设置如果是第一个sku编码, 可以加图片
     *
     * @param picture 如果是第一个sku编码, 可以加图片
     */
    public void setPicture(Integer picture) {
        this.picture = picture;
    }

    /**
     * 获取商家编码
     *
     * @return code - 商家编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置商家编码
     *
     * @param code 商家编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取商品二维码
     *
     * @return QRcode - 商品二维码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置商品二维码
     *
     * @param qrcode 商品二维码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", skuName=").append(skuName);
        sb.append(", attrValueItems=").append(attrValueItems);
        sb.append(", attrValueItemsFormat=").append(attrValueItemsFormat);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", price=").append(price);
        sb.append(", promotePrice=").append(promotePrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", stock=").append(stock);
        sb.append(", picture=").append(picture);
        sb.append(", code=").append(code);
        sb.append(", qrcode=").append(qrcode);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}