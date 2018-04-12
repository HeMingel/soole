package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_after_sales_service")
public class SlAfterSalesService implements Serializable {
    /**
     * 商品售后唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 订单明细id
     */
    @Column(name = "order_detail_id")
    private String orderDetailId;

    /**
     * 申请售后类型(1:退货退款 2:仅退款)
     */
    private Integer type;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 退款金额
     */
    private BigDecimal money;

    /**
     * 凭证图片地址
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private String shopId;

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
     * 审核状态(0:待审核 1:已通过 2:未通过)
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 申请说明
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品售后唯一标识
     *
     * @return id - 商品售后唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置商品售后唯一标识
     *
     * @param id 商品售后唯一标识
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取订单明细id
     *
     * @return order_detail_id - 订单明细id
     */
    public String getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * 设置订单明细id
     *
     * @param orderDetailId 订单明细id
     */
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId == null ? null : orderDetailId.trim();
    }

    /**
     * 获取申请售后类型(1:退货退款 2:仅退款)
     *
     * @return type - 申请售后类型(1:退货退款 2:仅退款)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置申请售后类型(1:退货退款 2:仅退款)
     *
     * @param type 申请售后类型(1:退货退款 2:仅退款)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取申请原因
     *
     * @return reason - 申请原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置申请原因
     *
     * @param reason 申请原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 获取退款金额
     *
     * @return money - 退款金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置退款金额
     *
     * @param money 退款金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取凭证图片地址
     *
     * @return image_url - 凭证图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置凭证图片地址
     *
     * @param imageUrl 凭证图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取店铺id
     *
     * @return shop_id - 店铺id
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺id
     *
     * @param shopId 店铺id
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
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
     * 获取审核状态(0:待审核 1:已通过 2:未通过)
     *
     * @return audit_status - 审核状态(0:待审核 1:已通过 2:未通过)
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态(0:待审核 1:已通过 2:未通过)
     *
     * @param auditStatus 审核状态(0:待审核 1:已通过 2:未通过)
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取申请说明
     *
     * @return remark - 申请说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置申请说明
     *
     * @param remark 申请说明
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", type=").append(type);
        sb.append(", reason=").append(reason);
        sb.append(", money=").append(money);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", productId=").append(productId);
        sb.append(", userId=").append(userId);
        sb.append(", shopId=").append(shopId);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", auditStatus=").append(auditStatus);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}