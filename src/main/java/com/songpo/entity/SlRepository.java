package com.songpo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_repository")
public class SlRepository implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 店铺唯一标识
     */
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 商品唯一标识
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 平台价格
     */
    private BigDecimal price;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modification_time")
    private String modificationTime;

    /**
     * 参考价格
     */
    @Column(name = "reference_price")
    private BigDecimal referencePrice;

    /**
     * 成本价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 拼团价
     */
    @Column(name = "collage_price")
    private BigDecimal collagePrice;

    /**
     * 个人价
     */
    @Column(name = "personal_price")
    private BigDecimal personalPrice;

    /**
     * 秒杀价
     */
    @Column(name = "price_spike")
    private BigDecimal priceSpike;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 是否包邮
     */
    @Column(name = "isShip")
    private Boolean isship;

    /**
     * 了豆
     */
    private Integer pulse;

    /**
     * 评论数量
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 销售数量
     */
    @Column(name = "sale_num")
    private Integer saleNum;

    /**
     * 销售类型:5钱6乐豆7钱+了豆
     */
    @Column(name = "sale_type")
    private Integer saleType;

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
     * 获取店铺唯一标识
     *
     * @return shop_id - 店铺唯一标识
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺唯一标识
     *
     * @param shopId 店铺唯一标识
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * 获取商品唯一标识
     *
     * @return product_id - 商品唯一标识
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品唯一标识
     *
     * @param productId 商品唯一标识
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取平台价格
     *
     * @return price - 平台价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置平台价格
     *
     * @param price 平台价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取折扣
     *
     * @return discount - 折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 设置折扣
     *
     * @param discount 折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 获取状态
     *
     * @return state - 状态
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取数量
     *
     * @return count - 数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置数量
     *
     * @param count 数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取评分
     *
     * @return score - 评分
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 设置评分
     *
     * @param score 评分
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modification_time - 修改时间
     */
    public String getModificationTime() {
        return modificationTime;
    }

    /**
     * 设置修改时间
     *
     * @param modificationTime 修改时间
     */
    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime == null ? null : modificationTime.trim();
    }

    /**
     * 获取参考价格
     *
     * @return reference_price - 参考价格
     */
    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    /**
     * 设置参考价格
     *
     * @param referencePrice 参考价格
     */
    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    /**
     * 获取成本价格
     *
     * @return cost_price - 成本价格
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价格
     *
     * @param costPrice 成本价格
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取拼团价
     *
     * @return collage_price - 拼团价
     */
    public BigDecimal getCollagePrice() {
        return collagePrice;
    }

    /**
     * 设置拼团价
     *
     * @param collagePrice 拼团价
     */
    public void setCollagePrice(BigDecimal collagePrice) {
        this.collagePrice = collagePrice;
    }

    /**
     * 获取个人价
     *
     * @return personal_price - 个人价
     */
    public BigDecimal getPersonalPrice() {
        return personalPrice;
    }

    /**
     * 设置个人价
     *
     * @param personalPrice 个人价
     */
    public void setPersonalPrice(BigDecimal personalPrice) {
        this.personalPrice = personalPrice;
    }

    /**
     * 获取秒杀价
     *
     * @return price_spike - 秒杀价
     */
    public BigDecimal getPriceSpike() {
        return priceSpike;
    }

    /**
     * 设置秒杀价
     *
     * @param priceSpike 秒杀价
     */
    public void setPriceSpike(BigDecimal priceSpike) {
        this.priceSpike = priceSpike;
    }

    /**
     * 获取重量
     *
     * @return weight - 重量
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 获取是否包邮
     *
     * @return isShip - 是否包邮
     */
    public Boolean getIsship() {
        return isship;
    }

    /**
     * 设置是否包邮
     *
     * @param isship 是否包邮
     */
    public void setIsship(Boolean isship) {
        this.isship = isship;
    }

    /**
     * 获取了豆
     *
     * @return pulse - 了豆
     */
    public Integer getPulse() {
        return pulse;
    }

    /**
     * 设置了豆
     *
     * @param pulse 了豆
     */
    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    /**
     * 获取评论数量
     *
     * @return comment_num - 评论数量
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置评论数量
     *
     * @param commentNum 评论数量
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 获取销售数量
     *
     * @return sale_num - 销售数量
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * 设置销售数量
     *
     * @param saleNum 销售数量
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 获取销售类型:5钱6乐豆7钱+了豆
     *
     * @return sale_type - 销售类型:5钱6乐豆7钱+了豆
     */
    public Integer getSaleType() {
        return saleType;
    }

    /**
     * 设置销售类型:5钱6乐豆7钱+了豆
     *
     * @param saleType 销售类型:5钱6乐豆7钱+了豆
     */
    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
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
        sb.append(", remark=").append(remark);
        sb.append(", price=").append(price);
        sb.append(", discount=").append(discount);
        sb.append(", state=").append(state);
        sb.append(", count=").append(count);
        sb.append(", score=").append(score);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", collagePrice=").append(collagePrice);
        sb.append(", personalPrice=").append(personalPrice);
        sb.append(", priceSpike=").append(priceSpike);
        sb.append(", weight=").append(weight);
        sb.append(", isship=").append(isship);
        sb.append(", pulse=").append(pulse);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", saleNum=").append(saleNum);
        sb.append(", saleType=").append(saleType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}