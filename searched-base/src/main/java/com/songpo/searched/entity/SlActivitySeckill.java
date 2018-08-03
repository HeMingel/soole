package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_activity_seckill")
public class SlActivitySeckill implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品ID（varhcar类型）
     */
    @Column(name = "product_old_id")
    private String productOldId;

    /**
     * 商品ID(int自增类型)
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 活动开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 商品秒杀价
     */
    @Column(name = "seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 总库存
     */
    @Column(name = "total_count")
    private Integer totalCount;

    /**
     * 总的规格库存 永远不变  用来统计秒杀出售的个数
     */
    @Column(name = "total_count_no_change")
    private Integer totalCountNoChange;

    /**
     * 是否可用 1可用  0 不可用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 最后更新时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品ID（varhcar类型）
     *
     * @return product_old_id - 商品ID（varhcar类型）
     */
    public String getProductOldId() {
        return productOldId;
    }

    /**
     * 设置商品ID（varhcar类型）
     *
     * @param productOldId 商品ID（varhcar类型）
     */
    public void setProductOldId(String productOldId) {
        this.productOldId = productOldId == null ? null : productOldId.trim();
    }

    /**
     * 获取商品ID(int自增类型)
     *
     * @return product_id - 商品ID(int自增类型)
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品ID(int自增类型)
     *
     * @param productId 商品ID(int自增类型)
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取活动开始时间
     *
     * @return start_time - 活动开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param startTime 活动开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_time - 活动结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endTime 活动结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取商品秒杀价
     *
     * @return seckill_price - 商品秒杀价
     */
    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    /**
     * 设置商品秒杀价
     *
     * @param seckillPrice 商品秒杀价
     */
    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    /**
     * 获取总库存
     *
     * @return total_count - 总库存
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总库存
     *
     * @param totalCount 总库存
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取总的规格库存 永远不变  用来统计秒杀出售的个数
     *
     * @return total_count_no_change - 总的规格库存 永远不变  用来统计秒杀出售的个数
     */
    public Integer getTotalCountNoChange() {
        return totalCountNoChange;
    }

    /**
     * 设置总的规格库存 永远不变  用来统计秒杀出售的个数
     *
     * @param totalCountNoChange 总的规格库存 永远不变  用来统计秒杀出售的个数
     */
    public void setTotalCountNoChange(Integer totalCountNoChange) {
        this.totalCountNoChange = totalCountNoChange;
    }

    /**
     * 获取是否可用 1可用  0 不可用
     *
     * @return enable - 是否可用 1可用  0 不可用
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否可用 1可用  0 不可用
     *
     * @param enable 是否可用 1可用  0 不可用
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_at - 最后更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateAt 最后更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productOldId=").append(productOldId);
        sb.append(", productId=").append(productId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", seckillPrice=").append(seckillPrice);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", totalCountNoChange=").append(totalCountNoChange);
        sb.append(", enable=").append(enable);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}