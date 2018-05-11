package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_partner_news")
public class SlPartnerNews implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 高级店铺审核ID
     */
    @Column(name = "top_store_id")
    private String topStoreId;

    /**
     * 股东姓名
     */
    @Column(name = "partner_name")
    private String partnerName;

    /**
     * 股东电话
     */
    @Column(name = "partner_phone")
    private String partnerPhone;

    /**
     * 股东地址
     */
    @Column(name = "partner_addr")
    private String partnerAddr;

    /**
     * 排序
     */
    @Column(name = "partner_order")
    private Integer partnerOrder;

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
     * 获取高级店铺审核ID
     *
     * @return top_store_id - 高级店铺审核ID
     */
    public String getTopStoreId() {
        return topStoreId;
    }

    /**
     * 设置高级店铺审核ID
     *
     * @param topStoreId 高级店铺审核ID
     */
    public void setTopStoreId(String topStoreId) {
        this.topStoreId = topStoreId == null ? null : topStoreId.trim();
    }

    /**
     * 获取股东姓名
     *
     * @return partner_name - 股东姓名
     */
    public String getPartnerName() {
        return partnerName;
    }

    /**
     * 设置股东姓名
     *
     * @param partnerName 股东姓名
     */
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName == null ? null : partnerName.trim();
    }

    /**
     * 获取股东电话
     *
     * @return partner_phone - 股东电话
     */
    public String getPartnerPhone() {
        return partnerPhone;
    }

    /**
     * 设置股东电话
     *
     * @param partnerPhone 股东电话
     */
    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone == null ? null : partnerPhone.trim();
    }

    /**
     * 获取股东地址
     *
     * @return partner_addr - 股东地址
     */
    public String getPartnerAddr() {
        return partnerAddr;
    }

    /**
     * 设置股东地址
     *
     * @param partnerAddr 股东地址
     */
    public void setPartnerAddr(String partnerAddr) {
        this.partnerAddr = partnerAddr == null ? null : partnerAddr.trim();
    }

    /**
     * 获取排序
     *
     * @return partner_order - 排序
     */
    public Integer getPartnerOrder() {
        return partnerOrder;
    }

    /**
     * 设置排序
     *
     * @param partnerOrder 排序
     */
    public void setPartnerOrder(Integer partnerOrder) {
        this.partnerOrder = partnerOrder;
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
        sb.append(", topStoreId=").append(topStoreId);
        sb.append(", partnerName=").append(partnerName);
        sb.append(", partnerPhone=").append(partnerPhone);
        sb.append(", partnerAddr=").append(partnerAddr);
        sb.append(", partnerOrder=").append(partnerOrder);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}