package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

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
     * 是否下架
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
     * 销售模式  1:普通 2：人气拼团 3：随心购 4：预售 5：豆赚 6：优品赚
     */
    @Column(name = "sales_mode")
    private Integer salesMode;

    /**
     * 店铺唯一标识符
     */
    @Column(name = "shop_id")
    private String shopId;

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
     * 获取是否下架
     *
     * @return sold_out - 是否下架
     */
    public Boolean getSoldOut() {
        return soldOut;
    }

    /**
     * 设置是否下架
     *
     * @param soldOut 是否下架
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
     * 获取销售模式  1:普通 2：人气拼团 3：随心购 4：预售 5：豆赚 6：优品赚
     *
     * @return sales_mode - 销售模式  1:普通 2：人气拼团 3：随心购 4：预售 5：豆赚 6：优品赚
     */
    public Integer getSalesMode() {
        return salesMode;
    }

    /**
     * 设置销售模式  1:普通 2：人气拼团 3：随心购 4：预售 5：豆赚 6：优品赚
     *
     * @param salesMode 销售模式  1:普通 2：人气拼团 3：随心购 4：预售 5：豆赚 6：优品赚
     */
    public void setSalesMode(Integer salesMode) {
        this.salesMode = salesMode;
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
        sb.append(", name=").append(name);
        sb.append(", remark=").append(remark);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", introduction=").append(introduction);
        sb.append(", detail=").append(detail);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", soldOut=").append(soldOut);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", referencePrice=").append(referencePrice);
        sb.append(", referencePulse=").append(referencePulse);
        sb.append(", salesMode=").append(salesMode);
        sb.append(", shopId=").append(shopId);
        sb.append(", saleType=").append(saleType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}