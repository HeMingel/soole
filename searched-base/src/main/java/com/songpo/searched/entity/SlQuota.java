package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_quota")
public class SlQuota implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 预售店铺额度
     */
    @Column(name = "quota_number")
    private Integer quotaNumber;

    /**
     * 添加时间
     */
    @Column(name = "quota_time")
    private String quotaTime;

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
     * 获取预售店铺额度
     *
     * @return quota_number - 预售店铺额度
     */
    public Integer getQuotaNumber() {
        return quotaNumber;
    }

    /**
     * 设置预售店铺额度
     *
     * @param quotaNumber 预售店铺额度
     */
    public void setQuotaNumber(Integer quotaNumber) {
        this.quotaNumber = quotaNumber;
    }

    /**
     * 获取添加时间
     *
     * @return quota_time - 添加时间
     */
    public String getQuotaTime() {
        return quotaTime;
    }

    /**
     * 设置添加时间
     *
     * @param quotaTime 添加时间
     */
    public void setQuotaTime(String quotaTime) {
        this.quotaTime = quotaTime == null ? null : quotaTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", quotaNumber=").append(quotaNumber);
        sb.append(", quotaTime=").append(quotaTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}