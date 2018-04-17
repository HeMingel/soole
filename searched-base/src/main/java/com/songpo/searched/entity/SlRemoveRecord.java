package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_remove_record")
public class SlRemoveRecord implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 兑换手机账号
     */
    private String mobile;

    /**
     * 兑换数量
     */
    private String voucher;

    /**
     * 兑换时间
     */
    @Column(name = "record_time")
    private String recordTime;

    private static final long serialVersionUID = 1L;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取兑换手机账号
     *
     * @return mobile - 兑换手机账号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置兑换手机账号
     *
     * @param mobile 兑换手机账号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取兑换数量
     *
     * @return voucher - 兑换数量
     */
    public String getVoucher() {
        return voucher;
    }

    /**
     * 设置兑换数量
     *
     * @param voucher 兑换数量
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher == null ? null : voucher.trim();
    }

    /**
     * 获取兑换时间
     *
     * @return record_time - 兑换时间
     */
    public String getRecordTime() {
        return recordTime;
    }

    /**
     * 设置兑换时间
     *
     * @param recordTime 兑换时间
     */
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime == null ? null : recordTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", mobile=").append(mobile);
        sb.append(", voucher=").append(voucher);
        sb.append(", recordTime=").append(recordTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}