package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_partner_news")
public class SlPartnerNews implements Serializable {
    private static final long serialVersionUID = 1L;
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}