package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "sl_shop")
public class SlShop implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 申请店铺表ID
     */
    @Column(name = "business_application_id")
    private String businessApplicationId;

    /**
     * 店主唯一标识符
     */
    @Column(name = "owner_id")
    private String ownerId;

    /**
     * 店铺经营类型id
     */
    @Column(name = "product_type_id")
    private String productTypeId;

    /**
     * 高级店铺预售额度ID
     */
    @Column(name = "quota_id")
    private String quotaId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 店铺logo
     */
    @Column(name = "logo_url")
    private String logoUrl;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 总销售额
     */
    @Column(name = "total_sales")
    private BigDecimal totalSales;

    /**
     * 资金
     */
    private BigDecimal money;

    /**
     * 金币
     */
    private Integer coin;

    /**
     * 银币
     */
    private Integer silver;

    /**
     * 评分
     */
    private Double rating;

    /**
     * 店铺电话
     */
    private String phone;

    /**
     * 1、普通店铺2、高级店铺
     */
    @Column(name = "shop_type")
    private Integer shopType;

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
     * 获取申请店铺表ID
     *
     * @return business_application_id - 申请店铺表ID
     */
    public String getBusinessApplicationId() {
        return businessApplicationId;
    }

    /**
     * 设置申请店铺表ID
     *
     * @param businessApplicationId 申请店铺表ID
     */
    public void setBusinessApplicationId(String businessApplicationId) {
        this.businessApplicationId = businessApplicationId == null ? null : businessApplicationId.trim();
    }

    /**
     * 获取店主唯一标识符
     *
     * @return owner_id - 店主唯一标识符
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 设置店主唯一标识符
     *
     * @param ownerId 店主唯一标识符
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    /**
     * 获取店铺经营类型id
     *
     * @return product_type_id - 店铺经营类型id
     */
    public String getProductTypeId() {
        return productTypeId;
    }

    /**
     * 设置店铺经营类型id
     *
     * @param productTypeId 店铺经营类型id
     */
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }

    /**
     * 获取高级店铺预售额度ID
     *
     * @return quota_id - 高级店铺预售额度ID
     */
    public String getQuotaId() {
        return quotaId;
    }

    /**
     * 设置高级店铺预售额度ID
     *
     * @param quotaId 高级店铺预售额度ID
     */
    public void setQuotaId(String quotaId) {
        this.quotaId = quotaId == null ? null : quotaId.trim();
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
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
     * 获取店铺logo
     *
     * @return logo_url - 店铺logo
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * 设置店铺logo
     *
     * @param logoUrl 店铺logo
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取维度
     *
     * @return latitude - 维度
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * 设置维度
     *
     * @param latitude 维度
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取总销售额
     *
     * @return total_sales - 总销售额
     */
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    /**
     * 设置总销售额
     *
     * @param totalSales 总销售额
     */
    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * 获取资金
     *
     * @return money - 资金
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置资金
     *
     * @param money 资金
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取金币
     *
     * @return coin - 金币
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置金币
     *
     * @param coin 金币
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
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
     * 获取评分
     *
     * @return rating - 评分
     */
    public Double getRating() {
        return rating;
    }

    /**
     * 设置评分
     *
     * @param rating 评分
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * 获取店铺电话
     *
     * @return phone - 店铺电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置店铺电话
     *
     * @param phone 店铺电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取1、普通店铺2、高级店铺
     *
     * @return shop_type - 1、普通店铺2、高级店铺
     */
    public Integer getShopType() {
        return shopType;
    }

    /**
     * 设置1、普通店铺2、高级店铺
     *
     * @param shopType 1、普通店铺2、高级店铺
     */
    public void setShopType(Integer shopType) {
        this.shopType = shopType;
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
        sb.append(", businessApplicationId=").append(businessApplicationId);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", quotaId=").append(quotaId);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", logoUrl=").append(logoUrl);
        sb.append(", address=").append(address);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", totalSales=").append(totalSales);
        sb.append(", money=").append(money);
        sb.append(", coin=").append(coin);
        sb.append(", silver=").append(silver);
        sb.append(", rating=").append(rating);
        sb.append(", phone=").append(phone);
        sb.append(", shopType=").append(shopType);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}