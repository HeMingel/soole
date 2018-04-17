package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "sl_shop")
public class SlShop implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 店主唯一标识符
     */
    @Column(name = "owner_id")
    private String ownerId;

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
     * 店铺经营类型id
     */
    @Column(name = "product_type_id")
    private String productTypeId;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ownerId=").append(ownerId);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", imageUrl=").append(imageUrl);
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
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}