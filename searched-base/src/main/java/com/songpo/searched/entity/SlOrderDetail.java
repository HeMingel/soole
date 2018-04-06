package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_order_detail")
public class SlOrderDetail implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 订单唯一标识
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单编号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 店铺唯一标识符
     */
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 店铺仓库唯一标识
     */
    @Column(name = "repository_id")
    private String repositoryId;

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
     * 商品图片
     */
    @Column(name = "product_image_url")
    private String productImageUrl;

    /**
     * 商品规格名称
     */
    @Column(name = "product_detail_group_name")
    private String productDetailGroupName;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单个商品价格
     */
    private BigDecimal price;

    /**
     * 单个商品扣除的银豆数量
     */
    @Column(name = "deduct_total_silver")
    private Integer deductTotalSilver;

    /**
     * 单个商品扣除的金豆数量
     */
    @Column(name = "deduct_total_gold")
    private Integer deductTotalGold;

    /**
     * 折扣
     */
    private Integer discount;

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
     * 快递费
     */
    @Column(name = "post_fee")
    private BigDecimal postFee;

    /**
     * 买家留言
     */
    @Column(name = "buyer_message")
    private String buyerMessage;

    /**
     * 返了豆数量(限推荐奖励活动)
     */
    @Column(name = "return_cash_pulse")
    private Integer returnCashPulse;

    /**
     * 发货时间
     */
    @Column(name = "shipping_time")
    private String shippingTime;

    /**
     * 物流单号
     */
    @Column(name = "ship_number")
    private String shipNumber;

    /**
     * 所需人数
     */
    @Column(name = "group_people")
    private Integer groupPeople;

    /**
     * 预售发货时间天数
     */
    @Column(name = "presell_shipments_days")
    private Integer presellShipmentsDays;

    /**
     * 返现金额(限全返模式)
     */
    @Column(name = "`return cash_money`")
    private BigDecimal returnCashMoney;

    /**
     * 下单返了豆(纯现金返了豆)
     */
    @Column(name = "place_order_return_pulse")
    private Integer placeOrderReturnPulse;

    /**
     * 推荐奖励奖励金额
     */
    @Column(name = "rewards_money")
    private BigDecimal rewardsMoney;

    /**
     * 奖励了豆数量(仅限推荐奖励活动模式)
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
     * 获取订单唯一标识
     *
     * @return order_id - 订单唯一标识
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单唯一标识
     *
     * @param orderId 订单唯一标识
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取订单编号
     *
     * @return serial_number - 订单编号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置订单编号
     *
     * @param serialNumber 订单编号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
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
     * 获取店铺仓库唯一标识
     *
     * @return repository_id - 店铺仓库唯一标识
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * 设置店铺仓库唯一标识
     *
     * @param repositoryId 店铺仓库唯一标识
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId == null ? null : repositoryId.trim();
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
     * 获取商品图片
     *
     * @return product_image_url - 商品图片
     */
    public String getProductImageUrl() {
        return productImageUrl;
    }

    /**
     * 设置商品图片
     *
     * @param productImageUrl 商品图片
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
     * 获取数量
     *
     * @return quantity - 数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置数量
     *
     * @param quantity 数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取单个商品价格
     *
     * @return price - 单个商品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置单个商品价格
     *
     * @param price 单个商品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取单个商品扣除的银豆数量
     *
     * @return deduct_total_silver - 单个商品扣除的银豆数量
     */
    public Integer getDeductTotalSilver() {
        return deductTotalSilver;
    }

    /**
     * 设置单个商品扣除的银豆数量
     *
     * @param deductTotalSilver 单个商品扣除的银豆数量
     */
    public void setDeductTotalSilver(Integer deductTotalSilver) {
        this.deductTotalSilver = deductTotalSilver;
    }

    /**
     * 获取单个商品扣除的金豆数量
     *
     * @return deduct_total_gold - 单个商品扣除的金豆数量
     */
    public Integer getDeductTotalGold() {
        return deductTotalGold;
    }

    /**
     * 设置单个商品扣除的金豆数量
     *
     * @param deductTotalGold 单个商品扣除的金豆数量
     */
    public void setDeductTotalGold(Integer deductTotalGold) {
        this.deductTotalGold = deductTotalGold;
    }

    /**
     * 获取折扣
     *
     * @return discount - 折扣
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * 设置折扣
     *
     * @param discount 折扣
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
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
     * 获取快递费
     *
     * @return post_fee - 快递费
     */
    public BigDecimal getPostFee() {
        return postFee;
    }

    /**
     * 设置快递费
     *
     * @param postFee 快递费
     */
    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    /**
     * 获取买家留言
     *
     * @return buyer_message - 买家留言
     */
    public String getBuyerMessage() {
        return buyerMessage;
    }

    /**
     * 设置买家留言
     *
     * @param buyerMessage 买家留言
     */
    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
    }

    /**
     * 获取返了豆数量(限推荐奖励活动)
     *
     * @return return_cash_pulse - 返了豆数量(限推荐奖励活动)
     */
    public Integer getReturnCashPulse() {
        return returnCashPulse;
    }

    /**
     * 设置返了豆数量(限推荐奖励活动)
     *
     * @param returnCashPulse 返了豆数量(限推荐奖励活动)
     */
    public void setReturnCashPulse(Integer returnCashPulse) {
        this.returnCashPulse = returnCashPulse;
    }

    /**
     * 获取发货时间
     *
     * @return shipping_time - 发货时间
     */
    public String getShippingTime() {
        return shippingTime;
    }

    /**
     * 设置发货时间
     *
     * @param shippingTime 发货时间
     */
    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime == null ? null : shippingTime.trim();
    }

    /**
     * 获取物流单号
     *
     * @return ship_number - 物流单号
     */
    public String getShipNumber() {
        return shipNumber;
    }

    /**
     * 设置物流单号
     *
     * @param shipNumber 物流单号
     */
    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber == null ? null : shipNumber.trim();
    }

    /**
     * 获取所需人数
     *
     * @return group_people - 所需人数
     */
    public Integer getGroupPeople() {
        return groupPeople;
    }

    /**
     * 设置所需人数
     *
     * @param groupPeople 所需人数
     */
    public void setGroupPeople(Integer groupPeople) {
        this.groupPeople = groupPeople;
    }

    /**
     * 获取预售发货时间天数
     *
     * @return presell_shipments_days - 预售发货时间天数
     */
    public Integer getPresellShipmentsDays() {
        return presellShipmentsDays;
    }

    /**
     * 设置预售发货时间天数
     *
     * @param presellShipmentsDays 预售发货时间天数
     */
    public void setPresellShipmentsDays(Integer presellShipmentsDays) {
        this.presellShipmentsDays = presellShipmentsDays;
    }

    /**
     * 获取返现金额(限全返模式)
     *
     * @return return cash_money - 返现金额(限全返模式)
     */
    public BigDecimal getReturnCashMoney() {
        return returnCashMoney;
    }

    /**
     * 设置返现金额(限全返模式)
     *
     * @param returnCashMoney 返现金额(限全返模式)
     */
    public void setReturnCashMoney(BigDecimal returnCashMoney) {
        this.returnCashMoney = returnCashMoney;
    }

    /**
     * 获取下单返了豆(纯现金返了豆)
     *
     * @return place_order_return_pulse - 下单返了豆(纯现金返了豆)
     */
    public Integer getPlaceOrderReturnPulse() {
        return placeOrderReturnPulse;
    }

    /**
     * 设置下单返了豆(纯现金返了豆)
     *
     * @param placeOrderReturnPulse 下单返了豆(纯现金返了豆)
     */
    public void setPlaceOrderReturnPulse(Integer placeOrderReturnPulse) {
        this.placeOrderReturnPulse = placeOrderReturnPulse;
    }

    /**
     * 获取推荐奖励奖励金额
     *
     * @return rewards_money - 推荐奖励奖励金额
     */
    public BigDecimal getRewardsMoney() {
        return rewardsMoney;
    }

    /**
     * 设置推荐奖励奖励金额
     *
     * @param rewardsMoney 推荐奖励奖励金额
     */
    public void setRewardsMoney(BigDecimal rewardsMoney) {
        this.rewardsMoney = rewardsMoney;
    }

    /**
     * 获取奖励了豆数量(仅限推荐奖励活动模式)
     *
     * @return rebate_pulse - 奖励了豆数量(仅限推荐奖励活动模式)
     */
    public Integer getRebatePulse() {
        return rebatePulse;
    }

    /**
     * 设置奖励了豆数量(仅限推荐奖励活动模式)
     *
     * @param rebatePulse 奖励了豆数量(仅限推荐奖励活动模式)
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
        sb.append(", orderId=").append(orderId);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", shopId=").append(shopId);
        sb.append(", repositoryId=").append(repositoryId);
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productImageUrl=").append(productImageUrl);
        sb.append(", productDetailGroupName=").append(productDetailGroupName);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", deductTotalSilver=").append(deductTotalSilver);
        sb.append(", deductTotalGold=").append(deductTotalGold);
        sb.append(", discount=").append(discount);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", postFee=").append(postFee);
        sb.append(", buyerMessage=").append(buyerMessage);
        sb.append(", returnCashPulse=").append(returnCashPulse);
        sb.append(", shippingTime=").append(shippingTime);
        sb.append(", shipNumber=").append(shipNumber);
        sb.append(", groupPeople=").append(groupPeople);
        sb.append(", presellShipmentsDays=").append(presellShipmentsDays);
        sb.append(", returnCashMoney=").append(returnCashMoney);
        sb.append(", placeOrderReturnPulse=").append(placeOrderReturnPulse);
        sb.append(", rewardsMoney=").append(rewardsMoney);
        sb.append(", rebatePulse=").append(rebatePulse);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}