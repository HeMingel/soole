package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_returns_detail")
public class SlReturnsDetail implements Serializable {
    /**
     * 用户返现记录唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 预售、消费返利 返现记录表id
     */
    @Column(name = "presell_returned_record_id")
    private String presellReturnedRecordId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 回款状态1.已发货 2.未发货 3.已逾期 4.可发货 5.订单交易完成
     */
    @Column(name = "returned_status")
    private Integer returnedStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 返款时间(yyyy-MM-dd)格式
     */
    @Column(name = "return_time")
    private String returnTime;

    /**
     * 返款金额
     */
    @Column(name = "return_money")
    private BigDecimal returnMoney;

    /**
     * 是否确认收货(true:确认 false:否)
     */
    @Column(name = "confirm_receipt")
    private Boolean confirmReceipt;

    /**
     * 是否已完成(true:已完成 false:未完成)
     */
    private Boolean completed;

    /**
     * 期数计数
     */
    @Column(name = "number_of_periods")
    private Integer numberOfPeriods;

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
     * 获取用户返现记录唯一标识符
     *
     * @return id - 用户返现记录唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户返现记录唯一标识符
     *
     * @param id 用户返现记录唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取预售、消费返利 返现记录表id
     *
     * @return presell_returned_record_id - 预售、消费返利 返现记录表id
     */
    public String getPresellReturnedRecordId() {
        return presellReturnedRecordId;
    }

    /**
     * 设置预售、消费返利 返现记录表id
     *
     * @param presellReturnedRecordId 预售、消费返利 返现记录表id
     */
    public void setPresellReturnedRecordId(String presellReturnedRecordId) {
        this.presellReturnedRecordId = presellReturnedRecordId == null ? null : presellReturnedRecordId.trim();
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取回款状态1.已发货 2.未发货 3.已逾期 4.可发货 5.订单交易完成
     *
     * @return returned_status - 回款状态1.已发货 2.未发货 3.已逾期 4.可发货 5.订单交易完成
     */
    public Integer getReturnedStatus() {
        return returnedStatus;
    }

    /**
     * 设置回款状态1.已发货 2.未发货 3.已逾期 4.可发货 5.订单交易完成
     *
     * @param returnedStatus 回款状态1.已发货 2.未发货 3.已逾期 4.可发货 5.订单交易完成
     */
    public void setReturnedStatus(Integer returnedStatus) {
        this.returnedStatus = returnedStatus;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 获取返款时间(yyyy-MM-dd)格式
     *
     * @return return_time - 返款时间(yyyy-MM-dd)格式
     */
    public String getReturnTime() {
        return returnTime;
    }

    /**
     * 设置返款时间(yyyy-MM-dd)格式
     *
     * @param returnTime 返款时间(yyyy-MM-dd)格式
     */
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime == null ? null : returnTime.trim();
    }

    /**
     * 获取返款金额
     *
     * @return return_money - 返款金额
     */
    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    /**
     * 设置返款金额
     *
     * @param returnMoney 返款金额
     */
    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    /**
     * 获取是否确认收货(true:确认 false:否)
     *
     * @return confirm_receipt - 是否确认收货(true:确认 false:否)
     */
    public Boolean getConfirmReceipt() {
        return confirmReceipt;
    }

    /**
     * 设置是否确认收货(true:确认 false:否)
     *
     * @param confirmReceipt 是否确认收货(true:确认 false:否)
     */
    public void setConfirmReceipt(Boolean confirmReceipt) {
        this.confirmReceipt = confirmReceipt;
    }

    /**
     * 获取是否已完成(true:已完成 false:未完成)
     *
     * @return completed - 是否已完成(true:已完成 false:未完成)
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * 设置是否已完成(true:已完成 false:未完成)
     *
     * @param completed 是否已完成(true:已完成 false:未完成)
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * 获取期数计数
     *
     * @return number_of_periods - 期数计数
     */
    public Integer getNumberOfPeriods() {
        return numberOfPeriods;
    }

    /**
     * 设置期数计数
     *
     * @param numberOfPeriods 期数计数
     */
    public void setNumberOfPeriods(Integer numberOfPeriods) {
        this.numberOfPeriods = numberOfPeriods;
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
        sb.append(", orderId=").append(orderId);
        sb.append(", presellReturnedRecordId=").append(presellReturnedRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", returnedStatus=").append(returnedStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", returnMoney=").append(returnMoney);
        sb.append(", confirmReceipt=").append(confirmReceipt);
        sb.append(", completed=").append(completed);
        sb.append(", numberOfPeriods=").append(numberOfPeriods);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}