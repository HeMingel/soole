package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_slb_type")
public class SlSlbType implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 类别 1:A轮 2:B轮 3:C轮  
     */
    @Column(name = "slb_type")
    private Integer slbType;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 赠送个数
     */
    @Column(name = "present_num")
    private BigDecimal presentNum;

    /**
     * 锁仓时间
     */
    @Column(name = "lock_time")
    private String lockTime;

    /**
     * 释放个数/月
     */
    @Column(name = "release_num")
    private BigDecimal releaseNum;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 修改时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取类别 1:A轮 2:B轮 3:C轮  
     *
     * @return slb_type - 类别 1:A轮 2:B轮 3:C轮  
     */
    public Integer getSlbType() {
        return slbType;
    }

    /**
     * 设置类别 1:A轮 2:B轮 3:C轮  
     *
     * @param slbType 类别 1:A轮 2:B轮 3:C轮  
     */
    public void setSlbType(Integer slbType) {
        this.slbType = slbType;
    }

    /**
     * 获取商品价格
     *
     * @return price - 商品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品价格
     *
     * @param price 商品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取赠送个数
     *
     * @return present_num - 赠送个数
     */
    public BigDecimal getPresentNum() {
        return presentNum;
    }

    /**
     * 设置赠送个数
     *
     * @param presentNum 赠送个数
     */
    public void setPresentNum(BigDecimal presentNum) {
        this.presentNum = presentNum;
    }

    /**
     * 获取锁仓时间
     *
     * @return lock_time - 锁仓时间
     */
    public String getLockTime() {
        return lockTime;
    }

    /**
     * 设置锁仓时间
     *
     * @param lockTime 锁仓时间
     */
    public void setLockTime(String lockTime) {
        this.lockTime = lockTime == null ? null : lockTime.trim();
    }

    /**
     * 获取释放个数/月
     *
     * @return release_num - 释放个数/月
     */
    public BigDecimal getReleaseNum() {
        return releaseNum;
    }

    /**
     * 设置释放个数/月
     *
     * @param releaseNum 释放个数/月
     */
    public void setReleaseNum(BigDecimal releaseNum) {
        this.releaseNum = releaseNum;
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
     * 获取修改时间
     *
     * @return update_at - 修改时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置修改时间
     *
     * @param updateAt 修改时间
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
        sb.append(", slbType=").append(slbType);
        sb.append(", price=").append(price);
        sb.append(", presentNum=").append(presentNum);
        sb.append(", lockTime=").append(lockTime);
        sb.append(", releaseNum=").append(releaseNum);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}