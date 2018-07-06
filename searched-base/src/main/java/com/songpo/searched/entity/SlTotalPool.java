package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_total_pool")
public class SlTotalPool implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 资金池金豆余额
     */
    @Column(name = "pool_coin")
    private Integer poolCoin;

    /**
     * 资金池银豆余额
     */
    @Column(name = "pool_silver")
    private Integer poolSilver;

    /**
     * 资金池搜了贝余额
     */
    @Column(name = "pool_slb")
    private BigDecimal poolSlb;

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
     * 获取资金池金豆余额
     *
     * @return pool_coin - 资金池金豆余额
     */
    public Integer getPoolCoin() {
        return poolCoin;
    }

    /**
     * 设置资金池金豆余额
     *
     * @param poolCoin 资金池金豆余额
     */
    public void setPoolCoin(Integer poolCoin) {
        this.poolCoin = poolCoin;
    }

    /**
     * 获取资金池银豆余额
     *
     * @return pool_silver - 资金池银豆余额
     */
    public Integer getPoolSilver() {
        return poolSilver;
    }

    /**
     * 设置资金池银豆余额
     *
     * @param poolSilver 资金池银豆余额
     */
    public void setPoolSilver(Integer poolSilver) {
        this.poolSilver = poolSilver;
    }

    /**
     * 获取资金池搜了贝余额
     *
     * @return pool_slb - 资金池搜了贝余额
     */
    public BigDecimal getPoolSlb() {
        return poolSlb;
    }

    /**
     * 设置资金池搜了贝余额
     *
     * @param poolSlb 资金池搜了贝余额
     */
    public void setPoolSlb(BigDecimal poolSlb) {
        this.poolSlb = poolSlb;
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
        sb.append(", poolCoin=").append(poolCoin);
        sb.append(", poolSilver=").append(poolSilver);
        sb.append(", poolSlb=").append(poolSlb);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}