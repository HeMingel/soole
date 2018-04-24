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
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

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
     * 商品规格名称
     */
    @Column(name = "product_detail_group_name")
    private String productDetailGroupName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 利润比例
     */
    @Column(name = "product_profit_ratio")
    private Float productProfitRatio;

    /**
     * 商品利润金额
     */
    @Column(name = "product_profit_money")
    private BigDecimal productProfitMoney;

    /**
     * 实际规格价格
     */
    private BigDecimal price;

    /**
     * 实际个人购买价格
     */
    @Column(name = "personal_price")
    private BigDecimal personalPrice;

    /**
     * 全返金额比例(限消费返利销售模式按商品价格)
     */
    @Column(name = "return_cash_ratio")
    private Float returnCashRatio;

    /**
     * 全返金额(消费返利模式)
     */
    @Column(name = "return_cash_money")
    private BigDecimal returnCashMoney;

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
     * 参考价格
     */
    @Column(name = "reference_price")
    private BigDecimal referencePrice;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 库存数量
     */
    private Integer count;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 成本价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 重量
     */
    private String weight;

    /**
     * 是否包邮(1:包邮 0:不包邮)
     */
    private Boolean ship;

    /**
     * 金币
     */
    private Integer gold;

    /**
     * 银币(暂时先消费银币不足时扣除金币)
     */
    private Integer silver;

    /**
     * 下单获得了豆数量(纯金钱的商品都会返)
     */
    @Column(name = "place_order_return_pulse")
    private Integer placeOrderReturnPulse;

    /**
     * 分享奖励金额(限分享奖励,商家额外分出来的利润;被分享人下单购买获得10%,分享人获得90%;)
     */
    @Column(name = "rewards_money")
    private BigDecimal rewardsMoney;

    /**
     * 分享奖励比例(限分享奖励)
     */
    @Column(name = "rewards_money_ratio")
    private Float rewardsMoneyRatio;

    /**
     * 首单获得了豆
     */
    @Column(name = "first_order_pulse")
    private Integer firstOrderPulse;

    /**
     * 助力购的所需人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

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
     * 获取商品规格名称
     *
     * @return product_detail_group_name - 商品规格名称
     */
    public String getProductDetailGroupName() {
        return productDetailGroupName;
    }

    /**
     * 设置商品规格名称
     *
     * @param productDetailGroupName 商品规格名称
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
     * 获取利润比例
     *
     * @return product_profit_ratio - 利润比例
     */
    public Float getProductProfitRatio() {
        return productProfitRatio;
    }

    /**
     * 设置利润比例
     *
     * @param productProfitRatio 利润比例
     */
    public void setProductProfitRatio(Float productProfitRatio) {
        this.productProfitRatio = productProfitRatio;
    }

    /**
     * 获取商品利润金额
     *
     * @return product_profit_money - 商品利润金额
     */
    public BigDecimal getProductProfitMoney() {
        return productProfitMoney;
    }

    /**
     * 设置商品利润金额
     *
     * @param productProfitMoney 商品利润金额
     */
    public void setProductProfitMoney(BigDecimal productProfitMoney) {
        this.productProfitMoney = productProfitMoney;
    }

    /**
     * 获取实际规格价格
     *
     * @return price - 实际规格价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置实际规格价格
     *
     * @param price 实际规格价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取实际个人购买价格
     *
     * @return personal_price - 实际个人购买价格
     */
    public BigDecimal getPersonalPrice() {
        return personalPrice;
    }

    /**
     * 设置实际个人购买价格
     *
     * @param personalPrice 实际个人购买价格
     */
    public void setPersonalPrice(BigDecimal personalPrice) {
        this.personalPrice = personalPrice;
    }

    /**
     * 获取全返金额比例(限消费返利销售模式按商品价格)
     *
     * @return return_cash_ratio - 全返金额比例(限消费返利销售模式按商品价格)
     */
    public Float getReturnCashRatio() {
        return returnCashRatio;
    }

    /**
     * 设置全返金额比例(限消费返利销售模式按商品价格)
     *
     * @param returnCashRatio 全返金额比例(限消费返利销售模式按商品价格)
     */
    public void setReturnCashRatio(Float returnCashRatio) {
        this.returnCashRatio = returnCashRatio;
    }

    /**
     * 获取全返金额(消费返利模式)
     *
     * @return return_cash_money - 全返金额(消费返利模式)
     */
    public BigDecimal getReturnCashMoney() {
        return returnCashMoney;
    }

    /**
     * 设置全返金额(消费返利模式)
     *
     * @param returnCashMoney 全返金额(消费返利模式)
     */
    public void setReturnCashMoney(BigDecimal returnCashMoney) {
        this.returnCashMoney = returnCashMoney;
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
     * 获取库存数量
     *
     * @return count - 库存数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置库存数量
     *
     * @param count 库存数量
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
     * 获取重量
     *
     * @return weight - 重量
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
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
     * 获取银币(暂时先消费银币不足时扣除金币)
     *
     * @return silver - 银币(暂时先消费银币不足时扣除金币)
     */
    public Integer getSilver() {
        return silver;
    }

    /**
     * 设置银币(暂时先消费银币不足时扣除金币)
     *
     * @param silver 银币(暂时先消费银币不足时扣除金币)
     */
    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    /**
     * 获取下单获得了豆数量(纯金钱的商品都会返)
     *
     * @return place_order_return_pulse - 下单获得了豆数量(纯金钱的商品都会返)
     */
    public Integer getPlaceOrderReturnPulse() {
        return placeOrderReturnPulse;
    }

    /**
     * 设置下单获得了豆数量(纯金钱的商品都会返)
     *
     * @param placeOrderReturnPulse 下单获得了豆数量(纯金钱的商品都会返)
     */
    public void setPlaceOrderReturnPulse(Integer placeOrderReturnPulse) {
        this.placeOrderReturnPulse = placeOrderReturnPulse;
    }

    /**
     * 获取分享奖励金额(限分享奖励,商家额外分出来的利润;被分享人下单购买获得10%,分享人获得90%;)
     *
     * @return rewards_money - 分享奖励金额(限分享奖励,商家额外分出来的利润;被分享人下单购买获得10%,分享人获得90%;)
     */
    public BigDecimal getRewardsMoney() {
        return rewardsMoney;
    }

    /**
     * 设置分享奖励金额(限分享奖励,商家额外分出来的利润;被分享人下单购买获得10%,分享人获得90%;)
     *
     * @param rewardsMoney 分享奖励金额(限分享奖励,商家额外分出来的利润;被分享人下单购买获得10%,分享人获得90%;)
     */
    public void setRewardsMoney(BigDecimal rewardsMoney) {
        this.rewardsMoney = rewardsMoney;
    }

    /**
     * 获取分享奖励比例(限分享奖励)
     *
     * @return rewards_money_ratio - 分享奖励比例(限分享奖励)
     */
    public Float getRewardsMoneyRatio() {
        return rewardsMoneyRatio;
    }

    /**
     * 设置分享奖励比例(限分享奖励)
     *
     * @param rewardsMoneyRatio 分享奖励比例(限分享奖励)
     */
    public void setRewardsMoneyRatio(Float rewardsMoneyRatio) {
        this.rewardsMoneyRatio = rewardsMoneyRatio;
    }

    /**
     * 获取首单获得了豆
     *
     * @return first_order_pulse - 首单获得了豆
     */
    public Integer getFirstOrderPulse() {
        return firstOrderPulse;
    }

    /**
     * 设置首单获得了豆
     *
     * @param firstOrderPulse 首单获得了豆
     */
    public void setFirstOrderPulse(Integer firstOrderPulse) {
        this.firstOrderPulse = firstOrderPulse;
    }

    /**
     * 获取助力购的所需人数
     *
     * @return people_num - 助力购的所需人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置助力购的所需人数
     *
     * @param peopleNum 助力购的所需人数
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
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
        sb.append(", productName=").append(productName);
        sb.append(", productImageUrl=").append(productImageUrl);
        sb.append(", productDetailGroupName=").append(productDetailGroupName);
        sb.append(", remark=").append(remark);
        sb.append(", productProfitRatio=").append(productProfitRatio);
        sb.append(", productProfitMoney=").append(productProfitMoney);
        sb.append(", price=").append(price);
        sb.append(", personalPrice=").append(personalPrice);
        sb.append(", returnCashRatio=").append(returnCashRatio);
        sb.append(", returnCashMoney=").append(returnCashMoney);
        sb.append(", postFee=").append(postFee);
        sb.append(", discount=").append(discount);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", state=").append(state);
        sb.append(", count=").append(count);
        sb.append(", score=").append(score);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", weight=").append(weight);
        sb.append(", ship=").append(ship);
        sb.append(", gold=").append(gold);
        sb.append(", silver=").append(silver);
        sb.append(", placeOrderReturnPulse=").append(placeOrderReturnPulse);
        sb.append(", rewardsMoney=").append(rewardsMoney);
        sb.append(", rewardsMoneyRatio=").append(rewardsMoneyRatio);
        sb.append(", firstOrderPulse=").append(firstOrderPulse);
        sb.append(", peopleNum=").append(peopleNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}