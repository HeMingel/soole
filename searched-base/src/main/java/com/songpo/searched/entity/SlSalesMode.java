package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_sales_mode")
public class SlSalesMode implements Serializable {
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
     * 是否可用金币支付
     */
    @Column(name = "use_gold")
    private Boolean useGold;

    /**
     * 是否可用银币支付
     */
    @Column(name = "use_silver")
    private Boolean useSilver;

    /**
     * 是否可用余额支付
     */
    @Column(name = "use_money")
    private Boolean useMoney;

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
     * 获取是否可用金币支付
     *
     * @return use_gold - 是否可用金币支付
     */
    public Boolean getUseGold() {
        return useGold;
    }

    /**
     * 设置是否可用金币支付
     *
     * @param useGold 是否可用金币支付
     */
    public void setUseGold(Boolean useGold) {
        this.useGold = useGold;
    }

    /**
     * 获取是否可用银币支付
     *
     * @return use_silver - 是否可用银币支付
     */
    public Boolean getUseSilver() {
        return useSilver;
    }

    /**
     * 设置是否可用银币支付
     *
     * @param useSilver 是否可用银币支付
     */
    public void setUseSilver(Boolean useSilver) {
        this.useSilver = useSilver;
    }

    /**
     * 获取是否可用余额支付
     *
     * @return use_money - 是否可用余额支付
     */
    public Boolean getUseMoney() {
        return useMoney;
    }

    /**
     * 设置是否可用余额支付
     *
     * @param useMoney 是否可用余额支付
     */
    public void setUseMoney(Boolean useMoney) {
        this.useMoney = useMoney;
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
        sb.append(", name=").append(name);
        sb.append(", remark=").append(remark);
        sb.append(", useGold=").append(useGold);
        sb.append(", useSilver=").append(useSilver);
        sb.append(", useMoney=").append(useMoney);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}