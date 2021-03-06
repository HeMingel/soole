package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
     * 0.未成功/失效 3:待发货 4:待收货/已发货 5:已完成/未评价 6:已评价 7:申请售后8:延期收货
     */
    @Column(name = "shipping_state")
    private Integer shippingState;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单个商品价格
     */
    private BigDecimal price;

    /**
     * 单个商品扣除的了豆数量
     */
    @Column(name = "deduct_total_silver")
    private Integer deductTotalSilver;

    /**
     * 确认收货时间
     */
    @Column(name = "confirm_receipt_time")
    private String confirmReceiptTime;

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
     * 发货时间
     */
    @Column(name = "shipping_time")
    private String shippingTime;

    /**
     * 快递唯一标识符
     */
    @Column(name = "ems_id")
    private String emsId;

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
     * 返现金额(限消费奖励模式)
     */
    @Column(name = "return_cash_money")
    private BigDecimal returnCashMoney;

    /**
     * 下单返了豆(纯现金返了豆)
     */
    @Column(name = "place_order_return_pulse")
    private Integer placeOrderReturnPulse;

    /**
     * 奖励金额(分享奖励金额)
     */
    @Column(name = "rewards_money")
    private BigDecimal rewardsMoney;

    /**
     * 分享人的唯一标识符
     */
    @Column(name = "share_of_people_id")
    private String shareOfPeopleId;

    /**
     * 商品活动id
     */
    @Column(name = "activity_product_id")
    private String activityProductId;

    /**
     * 1：（普通商品）普通订单 
2：（人气拼团）拼团订单 
3 : （云易购物）预售订单 
4 : （搜贝商品）搜贝商品 
5 : （广告赠送）消费奖励 
6 : （优惠购物）豆赚 7:分享奖励 8限时秒杀 9新人专享
     */
    private Integer type;

    /**
     * 财务确认状态(未支付给商家)1待确认2已确认
     */
    private Integer confirm;

    /**
     * 财务确认时间
     */
    @Column(name = "confirm_date")
    private String confirmDate;

    /**
     * 财务审核确认订单时使用1待确认2已确认
     */
    @Column(name = "check_state")
    private Byte checkState;

    /**
     * 财务审核人员信息
     */
    @Column(name = "check_name")
    private String checkName;

    /**
     * (暂未使用)修改人
     */
    private String modifier;

    /**
     * (暂未使用)修改时间
     */
    @Column(name = "modification_time")
    private String modificationTime;

    /**
     * (暂未使用)创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * (暂未使用)最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * (暂未使用)折扣
     */
    private Integer discount;

    /**
     * (暂未使用)奖励了豆数量(仅限推荐奖励活动模式)
     */
    @Column(name = "rebate_pulse")
    private Integer rebatePulse;

    /**
     * (暂未使用)返了豆数量(限推荐奖励活动)
     */
    @Column(name = "return_cash_pulse")
    private Integer returnCashPulse;

    /**
     * (暂未使用)单个商品扣除的金豆数量
     */
    @Column(name = "deduct_total_gold")
    private Integer deductTotalGold;

    /**
     * 是否是虚拟拼团.1:是;0:不是.
     */
    @Column(name = "is_virtual_spell_group")
    private Byte isVirtualSpellGroup;

    /**
     * 邀请人ID
     */
    @Column(name = "inviter_id")
    private Integer inviterId;

    /**
     * 是否延迟收货 1是 0否（默认）
     */
    @Column(name = "is_delayed")
    private Integer isDelayed;

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
     * 获取0.未成功/失效 3:待发货 4:待收货/已发货 5:已完成/未评价 6:已评价 7:申请售后8:延期收货
     *
     * @return shipping_state - 0.未成功/失效 3:待发货 4:待收货/已发货 5:已完成/未评价 6:已评价 7:申请售后8:延期收货
     */
    public Integer getShippingState() {
        return shippingState;
    }

    /**
     * 设置0.未成功/失效 3:待发货 4:待收货/已发货 5:已完成/未评价 6:已评价 7:申请售后8:延期收货
     *
     * @param shippingState 0.未成功/失效 3:待发货 4:待收货/已发货 5:已完成/未评价 6:已评价 7:申请售后8:延期收货
     */
    public void setShippingState(Integer shippingState) {
        this.shippingState = shippingState;
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
     * 获取单个商品扣除的了豆数量
     *
     * @return deduct_total_silver - 单个商品扣除的了豆数量
     */
    public Integer getDeductTotalSilver() {
        return deductTotalSilver;
    }

    /**
     * 设置单个商品扣除的了豆数量
     *
     * @param deductTotalSilver 单个商品扣除的了豆数量
     */
    public void setDeductTotalSilver(Integer deductTotalSilver) {
        this.deductTotalSilver = deductTotalSilver;
    }

    /**
     * 获取确认收货时间
     *
     * @return confirm_receipt_time - 确认收货时间
     */
    public String getConfirmReceiptTime() {
        return confirmReceiptTime;
    }

    /**
     * 设置确认收货时间
     *
     * @param confirmReceiptTime 确认收货时间
     */
    public void setConfirmReceiptTime(String confirmReceiptTime) {
        this.confirmReceiptTime = confirmReceiptTime == null ? null : confirmReceiptTime.trim();
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
     * 获取快递唯一标识符
     *
     * @return ems_id - 快递唯一标识符
     */
    public String getEmsId() {
        return emsId;
    }

    /**
     * 设置快递唯一标识符
     *
     * @param emsId 快递唯一标识符
     */
    public void setEmsId(String emsId) {
        this.emsId = emsId == null ? null : emsId.trim();
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
     * 获取返现金额(限消费奖励模式)
     *
     * @return return_cash_money - 返现金额(限消费奖励模式)
     */
    public BigDecimal getReturnCashMoney() {
        return returnCashMoney;
    }

    /**
     * 设置返现金额(限消费奖励模式)
     *
     * @param returnCashMoney 返现金额(限消费奖励模式)
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
     * 获取奖励金额(分享奖励金额)
     *
     * @return rewards_money - 奖励金额(分享奖励金额)
     */
    public BigDecimal getRewardsMoney() {
        return rewardsMoney;
    }

    /**
     * 设置奖励金额(分享奖励金额)
     *
     * @param rewardsMoney 奖励金额(分享奖励金额)
     */
    public void setRewardsMoney(BigDecimal rewardsMoney) {
        this.rewardsMoney = rewardsMoney;
    }

    /**
     * 获取分享人的唯一标识符
     *
     * @return share_of_people_id - 分享人的唯一标识符
     */
    public String getShareOfPeopleId() {
        return shareOfPeopleId;
    }

    /**
     * 设置分享人的唯一标识符
     *
     * @param shareOfPeopleId 分享人的唯一标识符
     */
    public void setShareOfPeopleId(String shareOfPeopleId) {
        this.shareOfPeopleId = shareOfPeopleId == null ? null : shareOfPeopleId.trim();
    }

    /**
     * 获取商品活动id
     *
     * @return activity_product_id - 商品活动id
     */
    public String getActivityProductId() {
        return activityProductId;
    }

    /**
     * 设置商品活动id
     *
     * @param activityProductId 商品活动id
     */
    public void setActivityProductId(String activityProductId) {
        this.activityProductId = activityProductId == null ? null : activityProductId.trim();
    }

    /**
     * 获取1：（普通商品）普通订单 
2：（人气拼团）拼团订单 
3 : （云易购物）预售订单 
4 : （搜贝商品）搜贝商品 
5 : （广告赠送）消费奖励 
6 : （优惠购物）豆赚 7:分享奖励 8限时秒杀 9新人专享
     *
     * @return type - 1：（普通商品）普通订单 
2：（人气拼团）拼团订单 
3 : （云易购物）预售订单 
4 : （搜贝商品）搜贝商品 
5 : （广告赠送）消费奖励 
6 : （优惠购物）豆赚 7:分享奖励 8限时秒杀 9新人专享
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：（普通商品）普通订单 
2：（人气拼团）拼团订单 
3 : （云易购物）预售订单 
4 : （搜贝商品）搜贝商品 
5 : （广告赠送）消费奖励 
6 : （优惠购物）豆赚 7:分享奖励 8限时秒杀 9新人专享
     *
     * @param type 1：（普通商品）普通订单 
2：（人气拼团）拼团订单 
3 : （云易购物）预售订单 
4 : （搜贝商品）搜贝商品 
5 : （广告赠送）消费奖励 
6 : （优惠购物）豆赚 7:分享奖励 8限时秒杀 9新人专享
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取财务确认状态(未支付给商家)1待确认2已确认
     *
     * @return confirm - 财务确认状态(未支付给商家)1待确认2已确认
     */
    public Integer getConfirm() {
        return confirm;
    }

    /**
     * 设置财务确认状态(未支付给商家)1待确认2已确认
     *
     * @param confirm 财务确认状态(未支付给商家)1待确认2已确认
     */
    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    /**
     * 获取财务确认时间
     *
     * @return confirm_date - 财务确认时间
     */
    public String getConfirmDate() {
        return confirmDate;
    }

    /**
     * 设置财务确认时间
     *
     * @param confirmDate 财务确认时间
     */
    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate == null ? null : confirmDate.trim();
    }

    /**
     * 获取财务审核确认订单时使用1待确认2已确认
     *
     * @return check_state - 财务审核确认订单时使用1待确认2已确认
     */
    public Byte getCheckState() {
        return checkState;
    }

    /**
     * 设置财务审核确认订单时使用1待确认2已确认
     *
     * @param checkState 财务审核确认订单时使用1待确认2已确认
     */
    public void setCheckState(Byte checkState) {
        this.checkState = checkState;
    }

    /**
     * 获取财务审核人员信息
     *
     * @return check_name - 财务审核人员信息
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * 设置财务审核人员信息
     *
     * @param checkName 财务审核人员信息
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName == null ? null : checkName.trim();
    }

    /**
     * 获取(暂未使用)修改人
     *
     * @return modifier - (暂未使用)修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置(暂未使用)修改人
     *
     * @param modifier (暂未使用)修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 获取(暂未使用)修改时间
     *
     * @return modification_time - (暂未使用)修改时间
     */
    public String getModificationTime() {
        return modificationTime;
    }

    /**
     * 设置(暂未使用)修改时间
     *
     * @param modificationTime (暂未使用)修改时间
     */
    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime == null ? null : modificationTime.trim();
    }

    /**
     * 获取(暂未使用)创建时间
     *
     * @return created_at - (暂未使用)创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置(暂未使用)创建时间
     *
     * @param createdAt (暂未使用)创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取(暂未使用)最后更新时间
     *
     * @return updated_at - (暂未使用)最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置(暂未使用)最后更新时间
     *
     * @param updatedAt (暂未使用)最后更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取(暂未使用)折扣
     *
     * @return discount - (暂未使用)折扣
     */
    public Integer getDiscount() {
        return discount;
    }

    /**
     * 设置(暂未使用)折扣
     *
     * @param discount (暂未使用)折扣
     */
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * 获取(暂未使用)奖励了豆数量(仅限推荐奖励活动模式)
     *
     * @return rebate_pulse - (暂未使用)奖励了豆数量(仅限推荐奖励活动模式)
     */
    public Integer getRebatePulse() {
        return rebatePulse;
    }

    /**
     * 设置(暂未使用)奖励了豆数量(仅限推荐奖励活动模式)
     *
     * @param rebatePulse (暂未使用)奖励了豆数量(仅限推荐奖励活动模式)
     */
    public void setRebatePulse(Integer rebatePulse) {
        this.rebatePulse = rebatePulse;
    }

    /**
     * 获取(暂未使用)返了豆数量(限推荐奖励活动)
     *
     * @return return_cash_pulse - (暂未使用)返了豆数量(限推荐奖励活动)
     */
    public Integer getReturnCashPulse() {
        return returnCashPulse;
    }

    /**
     * 设置(暂未使用)返了豆数量(限推荐奖励活动)
     *
     * @param returnCashPulse (暂未使用)返了豆数量(限推荐奖励活动)
     */
    public void setReturnCashPulse(Integer returnCashPulse) {
        this.returnCashPulse = returnCashPulse;
    }

    /**
     * 获取(暂未使用)单个商品扣除的金豆数量
     *
     * @return deduct_total_gold - (暂未使用)单个商品扣除的金豆数量
     */
    public Integer getDeductTotalGold() {
        return deductTotalGold;
    }

    /**
     * 设置(暂未使用)单个商品扣除的金豆数量
     *
     * @param deductTotalGold (暂未使用)单个商品扣除的金豆数量
     */
    public void setDeductTotalGold(Integer deductTotalGold) {
        this.deductTotalGold = deductTotalGold;
    }

    /**
     * 获取是否是虚拟拼团.1:是;0:不是.
     *
     * @return is_virtual_spell_group - 是否是虚拟拼团.1:是;0:不是.
     */
    public Byte getIsVirtualSpellGroup() {
        return isVirtualSpellGroup;
    }

    /**
     * 设置是否是虚拟拼团.1:是;0:不是.
     *
     * @param isVirtualSpellGroup 是否是虚拟拼团.1:是;0:不是.
     */
    public void setIsVirtualSpellGroup(Byte isVirtualSpellGroup) {
        this.isVirtualSpellGroup = isVirtualSpellGroup;
    }

    /**
     * 获取邀请人ID
     *
     * @return inviter_id - 邀请人ID
     */
    public Integer getInviterId() {
        return inviterId;
    }

    /**
     * 设置邀请人ID
     *
     * @param inviterId 邀请人ID
     */
    public void setInviterId(Integer inviterId) {
        this.inviterId = inviterId;
    }

    /**
     * 获取是否延迟收货 1是 0否（默认）
     *
     * @return is_delayed - 是否延迟收货 1是 0否（默认）
     */
    public Integer getIsDelayed() {
        return isDelayed;
    }

    /**
     * 设置是否延迟收货 1是 0否（默认）
     *
     * @param isDelayed 是否延迟收货 1是 0否（默认）
     */
    public void setIsDelayed(Integer isDelayed) {
        this.isDelayed = isDelayed;
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
        sb.append(", shippingState=").append(shippingState);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", deductTotalSilver=").append(deductTotalSilver);
        sb.append(", confirmReceiptTime=").append(confirmReceiptTime);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", postFee=").append(postFee);
        sb.append(", buyerMessage=").append(buyerMessage);
        sb.append(", shippingTime=").append(shippingTime);
        sb.append(", emsId=").append(emsId);
        sb.append(", shipNumber=").append(shipNumber);
        sb.append(", groupPeople=").append(groupPeople);
        sb.append(", presellShipmentsDays=").append(presellShipmentsDays);
        sb.append(", returnCashMoney=").append(returnCashMoney);
        sb.append(", placeOrderReturnPulse=").append(placeOrderReturnPulse);
        sb.append(", rewardsMoney=").append(rewardsMoney);
        sb.append(", shareOfPeopleId=").append(shareOfPeopleId);
        sb.append(", activityProductId=").append(activityProductId);
        sb.append(", type=").append(type);
        sb.append(", confirm=").append(confirm);
        sb.append(", confirmDate=").append(confirmDate);
        sb.append(", checkState=").append(checkState);
        sb.append(", checkName=").append(checkName);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", discount=").append(discount);
        sb.append(", rebatePulse=").append(rebatePulse);
        sb.append(", returnCashPulse=").append(returnCashPulse);
        sb.append(", deductTotalGold=").append(deductTotalGold);
        sb.append(", isVirtualSpellGroup=").append(isVirtualSpellGroup);
        sb.append(", inviterId=").append(inviterId);
        sb.append(", isDelayed=").append(isDelayed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}