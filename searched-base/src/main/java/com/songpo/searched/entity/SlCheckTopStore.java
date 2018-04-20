package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_check_top_store")
public class SlCheckTopStore implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private String shopId;
    /**
     * 预售额度ID
     */
    @Column(name = "quota_id")
    private String quotaId;
    /**
     * 申请时间
     */
    @Column(name = "apply_time")
    private Integer applyTime;
    /**
     * 审核时间
     */
    @Column(name = "check_time")
    private Integer checkTime;
    /**
     * 审核状态1待审核2已通过3已拒绝
     */
    @Column(name = "check_status")
    private Boolean checkStatus;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * 获取预售额度ID
     *
     * @return quota_id - 预售额度ID
     */
    public String getQuotaId() {
        return quotaId;
    }

    /**
     * 设置预售额度ID
     *
     * @param quotaId 预售额度ID
     */
    public void setQuotaId(String quotaId) {
        this.quotaId = quotaId == null ? null : quotaId.trim();
    }

    /**
     * 获取申请时间
     *
     * @return apply_time - 申请时间
     */
    public Integer getApplyTime() {
        return applyTime;
    }

    /**
     * 设置申请时间
     *
     * @param applyTime 申请时间
     */
    public void setApplyTime(Integer applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 获取审核时间
     *
     * @return check_time - 审核时间
     */
    public Integer getCheckTime() {
        return checkTime;
    }

    /**
     * 设置审核时间
     *
     * @param checkTime 审核时间
     */
    public void setCheckTime(Integer checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 获取审核状态1待审核2已通过3已拒绝
     *
     * @return check_status - 审核状态1待审核2已通过3已拒绝
     */
    public Boolean getCheckStatus() {
        return checkStatus;
    }

    /**
     * 设置审核状态1待审核2已通过3已拒绝
     *
     * @param checkStatus 审核状态1待审核2已通过3已拒绝
     */
    public void setCheckStatus(Boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", quotaId=").append(quotaId);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", checkStatus=").append(checkStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}