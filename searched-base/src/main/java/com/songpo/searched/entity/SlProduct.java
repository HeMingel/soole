package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_product")
public class SlProduct implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 详情
     */
    private String detail;

    /**
     * 商品类别
     */
    @Column(name = "product_type_id")
    private String productTypeId;

    /**
     * 是否下架(0:否 1:是)
     */
    @Column(name = "sold_out")
    private Boolean soldOut;

    /**
     * 评论数量
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 销量
     */
    @Column(name = "sales_volume")
    private Integer salesVolume;

    /**
     * 参考价格
     */
    @Column(name = "reference_price")
    private BigDecimal referencePrice;

    /**
     * 参考豆子
     */
    @Column(name = "reference_pulse")
    private Integer referencePulse;

    /**
     * 店铺唯一标识符
     */
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 销售模式唯一标识符
     */
    @Column(name = "sales_mode_id")
    private String salesModeId;

    /**
     * 列表展示最低价格
     */
    @Column(name = "min_price")
    private BigDecimal minPrice;

    /**
     * 预售发货天数
     */
    @Column(name = "presell_shipments_days")
    private Integer presellShipmentsDays;

    /**
     * 列表展示返现money(显示规格最高的)
     */
    @Column(name = "return_money")
    private BigDecimal returnMoney;

    /**
     * 列表展示返豆数量(显示规格最多的)
     */
    @Column(name = "return_pulse")
    private Integer returnPulse;

    /**
     * 所需人数
     */
    @Column(name = "group_people")
    private Integer groupPeople;

    /**
     * 商家奖励额度
     */
    @Column(name = "reward_value")
    private Double rewardValue;

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
     * 限定单数
     */
    @Column(name = "restrict_count")
    private Integer restrictCount;

    /**
     * 是否为推荐商品，0:否 1:是
     */
    private Integer recommend;

    /**
     * 拼团价格
     */
    @Column(name = "group_price")
    private BigDecimal groupPrice;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 获取图片
     *
     * @return image_url - 图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片
     *
     * @param imageUrl 图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取简介
     *
     * @return introduction - 简介
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置简介
     *
     * @param introduction 简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * 获取详情
     *
     * @return detail - 详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置详情
     *
     * @param detail 详情
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * 获取商品类别
     *
     * @return product_type_id - 商品类别
     */
    public String getProductTypeId() {
        return productTypeId;
    }

    /**
     * 设置商品类别
     *
     * @param productTypeId 商品类别
     */
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }

    /**
     * 获取是否下架(0:否 1:是)
     *
     * @return sold_out - 是否下架(0:否 1:是)
     */
    public Boolean getSoldOut() {
        return soldOut;
    }

    /**
     * 设置是否下架(0:否 1:是)
     *
     * @param soldOut 是否下架(0:否 1:是)
     */
    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
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
     * 获取销量
     *
     * @return sales_volume - 销量
     */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /**
     * 设置销量
     *
     * @param salesVolume 销量
     */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
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
     * 获取参考豆子
     *
     * @return reference_pulse - 参考豆子
     */
    public Integer getReferencePulse() {
        return referencePulse;
    }

    /**
     * 设置参考豆子
     *
     * @param referencePulse 参考豆子
     */
    public void setReferencePulse(Integer referencePulse) {
        this.referencePulse = referencePulse;
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
     * 获取列表展示最低价格
     *
     * @return min_price - 列表展示最低价格
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * 设置列表展示最低价格
     *
     * @param minPrice 列表展示最低价格
     */
    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * 获取预售发货天数
     *
     * @return presell_shipments_days - 预售发货天数
     */
    public Integer getPresellShipmentsDays() {
        return presellShipmentsDays;
    }

    /**
     * 设置预售发货天数
     *
     * @param presellShipmentsDays 预售发货天数
     */
    public void setPresellShipmentsDays(Integer presellShipmentsDays) {
        this.presellShipmentsDays = presellShipmentsDays;
    }

    /**
     * 获取列表展示返现money(显示规格最高的)
     *
     * @return return_money - 列表展示返现money(显示规格最高的)
     */
    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    /**
     * 设置列表展示返现money(显示规格最高的)
     *
     * @param returnMoney 列表展示返现money(显示规格最高的)
     */
    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    /**
     * 获取列表展示返豆数量(显示规格最多的)
     *
     * @return return_pulse - 列表展示返豆数量(显示规格最多的)
     */
    public Integer getReturnPulse() {
        return returnPulse;
    }

    /**
     * 设置列表展示返豆数量(显示规格最多的)
     *
     * @param returnPulse 列表展示返豆数量(显示规格最多的)
     */
    public void setReturnPulse(Integer returnPulse) {
        this.returnPulse = returnPulse;
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
     * 获取商家奖励额度
     *
     * @return reward_value - 商家奖励额度
     */
    public Double getRewardValue() {
        return rewardValue;
    }

    /**
     * 设置商家奖励额度
     *
     * @param rewardValue 商家奖励额度
     */
    public void setRewardValue(Double rewardValue) {
        this.rewardValue = rewardValue;
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
     * 获取限定单数
     *
     * @return restrict_count - 限定单数
     */
    public Integer getRestrictCount() {
        return restrictCount;
    }

    /**
     * 设置限定单数
     *
     * @param restrictCount 限定单数
     */
    public void setRestrictCount(Integer restrictCount) {
        this.restrictCount = restrictCount;
    }

    /**
     * 获取是否为推荐商品，0:否 1:是
     *
     * @return recommend - 是否为推荐商品，0:否 1:是
     */
    public Integer getRecommend() {
        return recommend;
    }

    /**
     * 设置是否为推荐商品，0:否 1:是
     *
     * @param recommend 是否为推荐商品，0:否 1:是
     */
    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    /**
     * 获取拼团价格
     *
     * @return group_price - 拼团价格
     */
    public BigDecimal getGroupPrice() {
        return groupPrice;
    }

    /**
     * 设置拼团价格
     *
     * @param groupPrice 拼团价格
     */
    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", remark=").append(remark);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", introduction=").append(introduction);
        sb.append(", detail=").append(detail);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", soldOut=").append(soldOut);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", referencePulse=").append(referencePulse);
        sb.append(", shopId=").append(shopId);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", minPrice=").append(minPrice);
        sb.append(", presellShipmentsDays=").append(presellShipmentsDays);
        sb.append(", returnMoney=").append(returnMoney);
        sb.append(", returnPulse=").append(returnPulse);
        sb.append(", groupPeople=").append(groupPeople);
        sb.append(", rewardValue=").append(rewardValue);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", restrictCount=").append(restrictCount);
        sb.append(", recommend=").append(recommend);
        sb.append(", groupPrice=").append(groupPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}