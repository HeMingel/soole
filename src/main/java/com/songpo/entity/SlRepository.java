package com.songpo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "sl_repository")
public class SlRepository implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 价格
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}