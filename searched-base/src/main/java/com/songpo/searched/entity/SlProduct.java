package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

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
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 是否为推荐商品，0:否 1:是
     */
    private Integer recommend;

    /**
     * 1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     */
    @Column(name = "sold_out")
    private Boolean soldOut;

    /**
     * 关键词
     */
    private String antistop;

    /**
     * 商品审核未通过原因
     */
    private String reason;

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
     * 获取1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     *
     * @return sold_out - 1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     */
    public Boolean getSoldOut() {
        return soldOut;
    }

    /**
     * 设置1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     *
     * @param soldOut 1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     */
    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    /**
     * 获取关键词
     *
     * @return antistop - 关键词
     */
    public String getAntistop() {
        return antistop;
    }

    /**
     * 设置关键词
     *
     * @param antistop 关键词
     */
    public void setAntistop(String antistop) {
        this.antistop = antistop == null ? null : antistop.trim();
    }

    /**
     * 获取商品审核未通过原因
     *
     * @return reason - 商品审核未通过原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置商品审核未通过原因
     *
     * @param reason 商品审核未通过原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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
        sb.append(", commentNum=").append(commentNum);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", shopId=").append(shopId);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", recommend=").append(recommend);
        sb.append(", soldOut=").append(soldOut);
        sb.append(", antistop=").append(antistop);
        sb.append(", reason=").append(reason);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}