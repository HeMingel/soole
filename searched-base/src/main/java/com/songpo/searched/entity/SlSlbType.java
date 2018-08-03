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
     * slb名字
     */
    @Column(name = "slb_state")
    private String slbState;

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
     * 锁仓开始时间
     */
    @Column(name = "lock_begin_date")
    private String lockBeginDate;

    /**
     * 锁仓结束时间
     */
    @Column(name = "lock_end_date")
    private String lockEndDate;

    /**
     * 释放个数/月
     */
    @Column(name = "release_num")
    private BigDecimal releaseNum;

    /**
     * 每次释放比例(单位%)
     */
    @Column(name = "release_percent")
    private BigDecimal releasePercent;

    /**
     * 释放批次
     */
    @Column(name = "release_batch")
    private Integer releaseBatch;

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
     * 获取slb名字
     *
     * @return slb_state - slb名字
     */
    public String getSlbState() {
        return slbState;
    }

    /**
     * 设置slb名字
     *
     * @param slbState slb名字
     */
    public void setSlbState(String slbState) {
        this.slbState = slbState == null ? null : slbState.trim();
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
     * 获取锁仓开始时间
     *
     * @return lock_begin_date - 锁仓开始时间
     */
    public String getLockBeginDate() {
        return lockBeginDate;
    }

    /**
     * 设置锁仓开始时间
     *
     * @param lockBeginDate 锁仓开始时间
     */
    public void setLockBeginDate(String lockBeginDate) {
        this.lockBeginDate = lockBeginDate == null ? null : lockBeginDate.trim();
    }

    /**
     * 获取锁仓结束时间
     *
     * @return lock_end_date - 锁仓结束时间
     */
    public String getLockEndDate() {
        return lockEndDate;
    }

    /**
     * 设置锁仓结束时间
     *
     * @param lockEndDate 锁仓结束时间
     */
    public void setLockEndDate(String lockEndDate) {
        this.lockEndDate = lockEndDate == null ? null : lockEndDate.trim();
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
     * 获取每次释放比例(单位%)
     *
     * @return release_percent - 每次释放比例(单位%)
     */
    public BigDecimal getReleasePercent() {
        return releasePercent;
    }

    /**
     * 设置每次释放比例(单位%)
     *
     * @param releasePercent 每次释放比例(单位%)
     */
    public void setReleasePercent(BigDecimal releasePercent) {
        this.releasePercent = releasePercent;
    }

    /**
     * 获取释放批次
     *
     * @return release_batch - 释放批次
     */
    public Integer getReleaseBatch() {
        return releaseBatch;
    }

    /**
     * 设置释放批次
     *
     * @param releaseBatch 释放批次
     */
    public void setReleaseBatch(Integer releaseBatch) {
        this.releaseBatch = releaseBatch;
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
        sb.append(", slbState=").append(slbState);
        sb.append(", slbType=").append(slbType);
        sb.append(", price=").append(price);
        sb.append(", presentNum=").append(presentNum);
        sb.append(", lockBeginDate=").append(lockBeginDate);
        sb.append(", lockEndDate=").append(lockEndDate);
        sb.append(", releaseNum=").append(releaseNum);
        sb.append(", releasePercent=").append(releasePercent);
        sb.append(", releaseBatch=").append(releaseBatch);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}