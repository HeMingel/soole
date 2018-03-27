package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_product_repository")
public class SlProductRepository implements Serializable {
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
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 规格明细图片
     */
    @Column(name = "product_image_url")
    private String productImageUrl;

    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 商品规格属性序列号
     */
    @Column(name = "product_detail_group_serial_number")
    private String productDetailGroupSerialNumber;

    /**
     * 商品规格属性组合名称
     */
    @Column(name = "product_detail_group_name")
    private String productDetailGroupName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 平台价格
     */
    private BigDecimal price;

    /**
     * 邮费
     */
    @Column(name = "post_fee")
    private BigDecimal postFee;

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
     * 是否包邮(1:包邮 0:不包邮)
     */
    private Boolean ship;

    /**
     * 金币
     */
    private Integer gold;

    /**
     * 银币
     */
    private Integer silver;

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
     * 是否可用金币支付
     */
    @Column(name = "use_gold")
    private Boolean useGold;

    /**
     * 是否可用银币支付
     */
    @Column(name = "use_silver")
    private Boolean useSilver;

    /**
     * 是否可用余额支付
     */
    @Column(name = "use_money")
    private Boolean useMoney;

    /**
     * 销售模式唯一标识符
     */
    @Column(name = "sales_mode_id")
    private String salesModeId;

    /**
     * 返了豆数量
     */
    @Column(name = "rebate_pulse")
    private Integer rebatePulse;

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
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * 获取规格明细图片
     *
     * @return product_image_url - 规格明细图片
     */
    public String getProductImageUrl() {
        return productImageUrl;
    }

    /**
     * 设置规格明细图片
     *
     * @param productImageUrl 规格明细图片
     */
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl == null ? null : productImageUrl.trim();
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
     * 获取商品规格属性序列号
     *
     * @return product_detail_group_serial_number - 商品规格属性序列号
     */
    public String getProductDetailGroupSerialNumber() {
        return productDetailGroupSerialNumber;
    }

    /**
     * 设置商品规格属性序列号
     *
     * @param productDetailGroupSerialNumber 商品规格属性序列号
     */
    public void setProductDetailGroupSerialNumber(String productDetailGroupSerialNumber) {
        this.productDetailGroupSerialNumber = productDetailGroupSerialNumber == null ? null : productDetailGroupSerialNumber.trim();
    }

    /**
     * 获取商品规格属性组合名称
     *
     * @return product_detail_group_name - 商品规格属性组合名称
     */
    public String getProductDetailGroupName() {
        return productDetailGroupName;
    }

    /**
     * 设置商品规格属性组合名称
     *
     * @param productDetailGroupName 商品规格属性组合名称
     */
    public void setProductDetailGroupName(String productDetailGroupName) {
        this.productDetailGroupName = productDetailGroupName == null ? null : productDetailGroupName.trim();
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
     * 获取邮费
     *
     * @return post_fee - 邮费
     */
    public BigDecimal getPostFee() {
        return postFee;
    }

    /**
     * 设置邮费
     *
     * @param postFee 邮费
     */
    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
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
     * 获取是否包邮(1:包邮 0:不包邮)
     *
     * @return ship - 是否包邮(1:包邮 0:不包邮)
     */
    public Boolean getShip() {
        return ship;
    }

    /**
     * 设置是否包邮(1:包邮 0:不包邮)
     *
     * @param ship 是否包邮(1:包邮 0:不包邮)
     */
    public void setShip(Boolean ship) {
        this.ship = ship;
    }

    /**
     * 获取金币
     *
     * @return gold - 金币
     */
    public Integer getGold() {
        return gold;
    }

    /**
     * 设置金币
     *
     * @param gold 金币
     */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    /**
     * 获取银币
     *
     * @return silver - 银币
     */
    public Integer getSilver() {
        return silver;
    }

    /**
     * 设置银币
     *
     * @param silver 银币
     */
    public void setSilver(Integer silver) {
        this.silver = silver;
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
     * 获取是否可用金币支付
     *
     * @return use_gold - 是否可用金币支付
     */
    public Boolean getUseGold() {
        return useGold;
    }

    /**
     * 设置是否可用金币支付
     *
     * @param useGold 是否可用金币支付
     */
    public void setUseGold(Boolean useGold) {
        this.useGold = useGold;
    }

    /**
     * 获取是否可用银币支付
     *
     * @return use_silver - 是否可用银币支付
     */
    public Boolean getUseSilver() {
        return useSilver;
    }

    /**
     * 设置是否可用银币支付
     *
     * @param useSilver 是否可用银币支付
     */
    public void setUseSilver(Boolean useSilver) {
        this.useSilver = useSilver;
    }

    /**
     * 获取是否可用余额支付
     *
     * @return use_money - 是否可用余额支付
     */
    public Boolean getUseMoney() {
        return useMoney;
    }

    /**
     * 设置是否可用余额支付
     *
     * @param useMoney 是否可用余额支付
     */
    public void setUseMoney(Boolean useMoney) {
        this.useMoney = useMoney;
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
     * 获取返了豆数量
     *
     * @return rebate_pulse - 返了豆数量
     */
    public Integer getRebatePulse() {
        return rebatePulse;
    }

    /**
     * 设置返了豆数量
     *
     * @param rebatePulse 返了豆数量
     */
    public void setRebatePulse(Integer rebatePulse) {
        this.rebatePulse = rebatePulse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", productName=").append(productName);
        sb.append(", productImageUrl=").append(productImageUrl);
        sb.append(", productId=").append(productId);
        sb.append(", productDetailGroupSerialNumber=").append(productDetailGroupSerialNumber);
        sb.append(", productDetailGroupName=").append(productDetailGroupName);
        sb.append(", remark=").append(remark);
        sb.append(", price=").append(price);
        sb.append(", postFee=").append(postFee);
        sb.append(", discount=").append(discount);
        sb.append(", state=").append(state);
        sb.append(", count=").append(count);
        sb.append(", score=").append(score);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", collagePrice=").append(collagePrice);
        sb.append(", personalPrice=").append(personalPrice);
        sb.append(", priceSpike=").append(priceSpike);
        sb.append(", weight=").append(weight);
        sb.append(", ship=").append(ship);
        sb.append(", gold=").append(gold);
        sb.append(", silver=").append(silver);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", saleNum=").append(saleNum);
        sb.append(", useGold=").append(useGold);
        sb.append(", useSilver=").append(useSilver);
        sb.append(", useMoney=").append(useMoney);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", rebatePulse=").append(rebatePulse);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}