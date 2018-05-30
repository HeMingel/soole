package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
     * 商品编号
     */
    @Column(name = "product_number")
    private Integer productNumber;

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
     * 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    @Column(name = "pro_check_time")
    private String proCheckTime;

    /**
     * 是否为推荐商品，0:否 1:是
     */
    private Integer recommend;

    /**
     * 邮费
     */
    private BigDecimal postage;

    /**
     * 1.上架审核通过 0.下架 booble型(1:true 0:false),2 待审核3未通过
     */
    @Column(name = "sold_out")
    private Boolean soldOut;

    /**
     * 逻辑删除0不删除1删除
     */
    private Boolean del;

    /**
     * 第二版运费修改  1.包邮2.部分地区不包邮(李鑫添加)
     */
    private Boolean ship;

    /**
     * （暂未使用）关键词
     */
    private String antistop;

    /**
     * （暂未使用）商品审核未通过原因
     */
    private String reason;

    /**
     * （暂时未用）备注
     */
    private String remark;

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

    /**
     * 虚拟销量
     */
    @Column(name = "sales_virtual")
    private Integer salesVirtual;

    /**
     * 商品详情（存放商品的图文和详情图片 h5展示）
     */
    private String detail;

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
     * 获取商品编号
     *
     * @return product_number - 商品编号
     */
    public Integer getProductNumber() {
        return productNumber;
    }

    /**
     * 设置商品编号
     *
     * @param productNumber 商品编号
     */
    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
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
     * 获取审核时间(预售商品/了豆商品/消费返利商品审核时间)
     *
     * @return pro_check_time - 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    public String getProCheckTime() {
        return proCheckTime;
    }

    /**
     * 设置审核时间(预售商品/了豆商品/消费返利商品审核时间)
     *
     * @param proCheckTime 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    public void setProCheckTime(String proCheckTime) {
        this.proCheckTime = proCheckTime == null ? null : proCheckTime.trim();
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
     * 获取邮费
     *
     * @return postage - 邮费
     */
    public BigDecimal getPostage() {
        return postage;
    }

    /**
     * 设置邮费
     *
     * @param postage 邮费
     */
    public void setPostage(BigDecimal postage) {
        this.postage = postage;
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
     * 获取逻辑删除0不删除1删除
     *
     * @return del - 逻辑删除0不删除1删除
     */
    public Boolean getDel() {
        return del;
    }

    /**
     * 设置逻辑删除0不删除1删除
     *
     * @param del 逻辑删除0不删除1删除
     */
    public void setDel(Boolean del) {
        this.del = del;
    }

    /**
     * 获取第二版运费修改  1.包邮2.部分地区不包邮(李鑫添加)
     *
     * @return ship - 第二版运费修改  1.包邮2.部分地区不包邮(李鑫添加)
     */
    public Boolean getShip() {
        return ship;
    }

    /**
     * 设置第二版运费修改  1.包邮2.部分地区不包邮(李鑫添加)
     *
     * @param ship 第二版运费修改  1.包邮2.部分地区不包邮(李鑫添加)
     */
    public void setShip(Boolean ship) {
        this.ship = ship;
    }

    /**
     * 获取（暂未使用）关键词
     *
     * @return antistop - （暂未使用）关键词
     */
    public String getAntistop() {
        return antistop;
    }

    /**
     * 设置（暂未使用）关键词
     *
     * @param antistop （暂未使用）关键词
     */
    public void setAntistop(String antistop) {
        this.antistop = antistop == null ? null : antistop.trim();
    }

    /**
     * 获取（暂未使用）商品审核未通过原因
     *
     * @return reason - （暂未使用）商品审核未通过原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置（暂未使用）商品审核未通过原因
     *
     * @param reason （暂未使用）商品审核未通过原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 获取（暂时未用）备注
     *
     * @return remark - （暂时未用）备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置（暂时未用）备注
     *
     * @param remark （暂时未用）备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    /**
     * 获取虚拟销量
     *
     * @return sales_virtual - 虚拟销量
     */
    public Integer getSalesVirtual() {
        return salesVirtual;
    }

    /**
     * 设置虚拟销量
     *
     * @param salesVirtual 虚拟销量
     */
    public void setSalesVirtual(Integer salesVirtual) {
        this.salesVirtual = salesVirtual;
    }

    /**
     * 获取商品详情（存放商品的图文和详情图片 h5展示）
     *
     * @return detail - 商品详情（存放商品的图文和详情图片 h5展示）
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置商品详情（存放商品的图文和详情图片 h5展示）
     *
     * @param detail 商品详情（存放商品的图文和详情图片 h5展示）
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", productNumber=").append(productNumber);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", introduction=").append(introduction);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", shopId=").append(shopId);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", proCheckTime=").append(proCheckTime);
        sb.append(", recommend=").append(recommend);
        sb.append(", postage=").append(postage);
        sb.append(", soldOut=").append(soldOut);
        sb.append(", del=").append(del);
        sb.append(", ship=").append(ship);
        sb.append(", antistop=").append(antistop);
        sb.append(", reason=").append(reason);
        sb.append(", remark=").append(remark);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", salesVirtual=").append(salesVirtual);
        sb.append(", detail=").append(detail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}