package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "sl_activity_product")
public class SlActivityProduct implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 活动唯一标识符
     */
    @Column(name = "activity_id")
    private String activityId;

    /**
     * 活动仓库规格序号
     */
    @Column(name = "activity_product_repository_serial_number")
    private String activityProductRepositorySerialNumber;

    /**
     * 活动价格
     */
    private BigDecimal price;

    /**
     * 参考价格
     */
    @Column(name = "reference_price")
    private BigDecimal referencePrice;

    /**
     * 活动了豆
     */
    private Integer peas;

    /**
     * 个人价格(拼团使用)
     */
    @Column(name = "personal_price")
    private BigDecimal personalPrice;

    /**
     * 活动开始时间(无活动除外，必须设置开始时间)
     */
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 活动结束时间(无活动除外，必须设置开始时间)
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 参与活动的商品数量
     */
    private Integer count;

    /**
     * 拼团 所需人数
     */
    @Column(name = "people_num")
    private Integer peopleNum;

    /**
     * 已下单(已支付)人数
     */
    @Column(name = "orders_num")
    private Integer ordersNum;

    /**
     * 剩余总商品数量
     */
    @Column(name = "surplus_product_count")
    private Integer surplusProductCount;

    /**
     * 奖励金豆数量(只限新人专享10%，列表展示奖励数量:取该商品规格奖励最多的)
     */
    @Column(name = "award_peas_counts")
    private Double awardPeasCounts;

    /**
     * 奖励金额(只限分享奖励，商家自定义让利金额，后台设定)
     */
    @Column(name = "awward_money")
    private Double awwardMoney;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 该活动商品限制数量
     */
    @Column(name = "restrict_count")
    private Integer restrictCount;

    /**
     * 预售总发货天数
     */
    @Column(name = "presell_shipments_days")
    private Integer presellShipmentsDays;

    /**
     * 是否启用(1:启用 0;下架)
     */
    private Boolean enabled;

    /**
     * （暂未使用）参考豆子
     */
    @Column(name = "reference_peas")
    private Integer referencePeas;

    /**
     * （暂未使用）商家奖励额度
     */
    @Column(name = "reward_value")
    private Double rewardValue;

    /**
     * （暂未使用）列表展示返现money(显示规格最高的)
     */
    @Column(name = "return_money")
    private BigDecimal returnMoney;

    /**
     * （暂未使用）列表展示返豆数量(显示规格最多的)
     */
    @Column(name = "return_peas")
    private Integer returnPeas;

    /**
     * （不使用）（分享奖励活动和其他活动可以共存）是否存在分享奖励的id，若存在，则说明该商品参加分享奖励，不存在，即不参加分享奖励
     */
    @Column(name = "sharing_rewards_id")
    private String sharingRewardsId;

    /**
     * 分组id
     */
    @Column(name = "group_id")
    private String groupId;

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
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取活动唯一标识符
     *
     * @return activity_id - 活动唯一标识符
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * 设置活动唯一标识符
     *
     * @param activityId 活动唯一标识符
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    /**
     * 获取活动仓库规格序号
     *
     * @return activity_product_repository_serial_number - 活动仓库规格序号
     */
    public String getActivityProductRepositorySerialNumber() {
        return activityProductRepositorySerialNumber;
    }

    /**
     * 设置活动仓库规格序号
     *
     * @param activityProductRepositorySerialNumber 活动仓库规格序号
     */
    public void setActivityProductRepositorySerialNumber(String activityProductRepositorySerialNumber) {
        this.activityProductRepositorySerialNumber = activityProductRepositorySerialNumber == null ? null : activityProductRepositorySerialNumber.trim();
    }

    /**
     * 获取活动价格
     *
     * @return price - 活动价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置活动价格
     *
     * @param price 活动价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取活动了豆
     *
     * @return peas - 活动了豆
     */
    public Integer getPeas() {
        return peas;
    }

    /**
     * 设置活动了豆
     *
     * @param peas 活动了豆
     */
    public void setPeas(Integer peas) {
        this.peas = peas;
    }

    /**
     * 获取个人价格(拼团使用)
     *
     * @return personal_price - 个人价格(拼团使用)
     */
    public BigDecimal getPersonalPrice() {
        return personalPrice;
    }

    /**
     * 设置个人价格(拼团使用)
     *
     * @param personalPrice 个人价格(拼团使用)
     */
    public void setPersonalPrice(BigDecimal personalPrice) {
        this.personalPrice = personalPrice;
    }

    /**
     * 获取活动开始时间(无活动除外，必须设置开始时间)
     *
     * @return begin_time - 活动开始时间(无活动除外，必须设置开始时间)
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置活动开始时间(无活动除外，必须设置开始时间)
     *
     * @param beginTime 活动开始时间(无活动除外，必须设置开始时间)
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    /**
     * 获取活动结束时间(无活动除外，必须设置开始时间)
     *
     * @return end_time - 活动结束时间(无活动除外，必须设置开始时间)
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间(无活动除外，必须设置开始时间)
     *
     * @param endTime 活动结束时间(无活动除外，必须设置开始时间)
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    /**
     * 获取参与活动的商品数量
     *
     * @return count - 参与活动的商品数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置参与活动的商品数量
     *
     * @param count 参与活动的商品数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取拼团 所需人数
     *
     * @return people_num - 拼团 所需人数
     */
    public Integer getPeopleNum() {
        return peopleNum;
    }

    /**
     * 设置拼团 所需人数
     *
     * @param peopleNum 拼团 所需人数
     */
    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    /**
     * 获取已下单(已支付)人数
     *
     * @return orders_num - 已下单(已支付)人数
     */
    public Integer getOrdersNum() {
        return ordersNum;
    }

    /**
     * 设置已下单(已支付)人数
     *
     * @param ordersNum 已下单(已支付)人数
     */
    public void setOrdersNum(Integer ordersNum) {
        this.ordersNum = ordersNum;
    }

    /**
     * 获取剩余总商品数量
     *
     * @return surplus_product_count - 剩余总商品数量
     */
    public Integer getSurplusProductCount() {
        return surplusProductCount;
    }

    /**
     * 设置剩余总商品数量
     *
     * @param surplusProductCount 剩余总商品数量
     */
    public void setSurplusProductCount(Integer surplusProductCount) {
        this.surplusProductCount = surplusProductCount;
    }

    /**
     * 获取奖励金豆数量(只限新人专享10%，列表展示奖励数量:取该商品规格奖励最多的)
     *
     * @return award_peas_counts - 奖励金豆数量(只限新人专享10%，列表展示奖励数量:取该商品规格奖励最多的)
     */
    public Double getAwardPeasCounts() {
        return awardPeasCounts;
    }

    /**
     * 设置奖励金豆数量(只限新人专享10%，列表展示奖励数量:取该商品规格奖励最多的)
     *
     * @param awardPeasCounts 奖励金豆数量(只限新人专享10%，列表展示奖励数量:取该商品规格奖励最多的)
     */
    public void setAwardPeasCounts(Double awardPeasCounts) {
        this.awardPeasCounts = awardPeasCounts;
    }

    /**
     * 获取奖励金额(只限分享奖励，商家自定义让利金额，后台设定)
     *
     * @return awward_money - 奖励金额(只限分享奖励，商家自定义让利金额，后台设定)
     */
    public Double getAwwardMoney() {
        return awwardMoney;
    }

    /**
     * 设置奖励金额(只限分享奖励，商家自定义让利金额，后台设定)
     *
     * @param awwardMoney 奖励金额(只限分享奖励，商家自定义让利金额，后台设定)
     */
    public void setAwwardMoney(Double awwardMoney) {
        this.awwardMoney = awwardMoney;
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
     * 获取该活动商品限制数量
     *
     * @return restrict_count - 该活动商品限制数量
     */
    public Integer getRestrictCount() {
        return restrictCount;
    }

    /**
     * 设置该活动商品限制数量
     *
     * @param restrictCount 该活动商品限制数量
     */
    public void setRestrictCount(Integer restrictCount) {
        this.restrictCount = restrictCount;
    }

    /**
     * 获取预售总发货天数
     *
     * @return presell_shipments_days - 预售总发货天数
     */
    public Integer getPresellShipmentsDays() {
        return presellShipmentsDays;
    }

    /**
     * 设置预售总发货天数
     *
     * @param presellShipmentsDays 预售总发货天数
     */
    public void setPresellShipmentsDays(Integer presellShipmentsDays) {
        this.presellShipmentsDays = presellShipmentsDays;
    }

    /**
     * 获取是否启用(1:启用 0;下架)
     *
     * @return enabled - 是否启用(1:启用 0;下架)
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用(1:启用 0;下架)
     *
     * @param enabled 是否启用(1:启用 0;下架)
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取（暂未使用）参考豆子
     *
     * @return reference_peas - （暂未使用）参考豆子
     */
    public Integer getReferencePeas() {
        return referencePeas;
    }

    /**
     * 设置（暂未使用）参考豆子
     *
     * @param referencePeas （暂未使用）参考豆子
     */
    public void setReferencePeas(Integer referencePeas) {
        this.referencePeas = referencePeas;
    }

    /**
     * 获取（暂未使用）商家奖励额度
     *
     * @return reward_value - （暂未使用）商家奖励额度
     */
    public Double getRewardValue() {
        return rewardValue;
    }

    /**
     * 设置（暂未使用）商家奖励额度
     *
     * @param rewardValue （暂未使用）商家奖励额度
     */
    public void setRewardValue(Double rewardValue) {
        this.rewardValue = rewardValue;
    }

    /**
     * 获取（暂未使用）列表展示返现money(显示规格最高的)
     *
     * @return return_money - （暂未使用）列表展示返现money(显示规格最高的)
     */
    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    /**
     * 设置（暂未使用）列表展示返现money(显示规格最高的)
     *
     * @param returnMoney （暂未使用）列表展示返现money(显示规格最高的)
     */
    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    /**
     * 获取（暂未使用）列表展示返豆数量(显示规格最多的)
     *
     * @return return_peas - （暂未使用）列表展示返豆数量(显示规格最多的)
     */
    public Integer getReturnPeas() {
        return returnPeas;
    }

    /**
     * 设置（暂未使用）列表展示返豆数量(显示规格最多的)
     *
     * @param returnPeas （暂未使用）列表展示返豆数量(显示规格最多的)
     */
    public void setReturnPeas(Integer returnPeas) {
        this.returnPeas = returnPeas;
    }

    /**
     * 获取（不使用）（分享奖励活动和其他活动可以共存）是否存在分享奖励的id，若存在，则说明该商品参加分享奖励，不存在，即不参加分享奖励
     *
     * @return sharing_rewards_id - （不使用）（分享奖励活动和其他活动可以共存）是否存在分享奖励的id，若存在，则说明该商品参加分享奖励，不存在，即不参加分享奖励
     */
    public String getSharingRewardsId() {
        return sharingRewardsId;
    }

    /**
     * 设置（不使用）（分享奖励活动和其他活动可以共存）是否存在分享奖励的id，若存在，则说明该商品参加分享奖励，不存在，即不参加分享奖励
     *
     * @param sharingRewardsId （不使用）（分享奖励活动和其他活动可以共存）是否存在分享奖励的id，若存在，则说明该商品参加分享奖励，不存在，即不参加分享奖励
     */
    public void setSharingRewardsId(String sharingRewardsId) {
        this.sharingRewardsId = sharingRewardsId == null ? null : sharingRewardsId.trim();
    }

    /**
     * 获取分组id
     *
     * @return group_id - 分组id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置分组id
     *
     * @param groupId 分组id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
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
        sb.append(", activityId=").append(activityId);
        sb.append(", activityProductRepositorySerialNumber=").append(activityProductRepositorySerialNumber);
        sb.append(", price=").append(price);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", peas=").append(peas);
        sb.append(", personalPrice=").append(personalPrice);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", count=").append(count);
        sb.append(", peopleNum=").append(peopleNum);
        sb.append(", ordersNum=").append(ordersNum);
        sb.append(", surplusProductCount=").append(surplusProductCount);
        sb.append(", awardPeasCounts=").append(awardPeasCounts);
        sb.append(", awwardMoney=").append(awwardMoney);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append(", restrictCount=").append(restrictCount);
        sb.append(", presellShipmentsDays=").append(presellShipmentsDays);
        sb.append(", enabled=").append(enabled);
        sb.append(", referencePeas=").append(referencePeas);
        sb.append(", rewardValue=").append(rewardValue);
        sb.append(", returnMoney=").append(returnMoney);
        sb.append(", returnPeas=").append(returnPeas);
        sb.append(", sharingRewardsId=").append(sharingRewardsId);
        sb.append(", groupId=").append(groupId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}