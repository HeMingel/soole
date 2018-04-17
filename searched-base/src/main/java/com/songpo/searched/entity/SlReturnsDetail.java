package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "sl_returns_detail")
public class SlReturnsDetail implements Serializable {
    /**
     * 用户返还金额明细
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
     * 回款状态1.未返 2.已返 3.已逾期 4.可返
     */
    @Column(name = "returned_status")
    private Integer returnedStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 返款时间
     */
    @Column(name = "return_time")
    private String returnTime;

    /**
     * 返款金额
     */
    @Column(name = "return_money")
    private BigDecimal returnMoney;

    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private String orderId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户返还金额明细
     *
     * @return id - 用户返还金额明细
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户返还金额明细
     *
     * @param id 用户返还金额明细
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * 获取回款状态1.未返 2.已返 3.已逾期 4.可返
     *
     * @return returned_status - 回款状态1.未返 2.已返 3.已逾期 4.可返
     */
    public Integer getReturnedStatus() {
        return returnedStatus;
    }

    /**
     * 设置回款状态1.未返 2.已返 3.已逾期 4.可返
     *
     * @param returnedStatus 回款状态1.未返 2.已返 3.已逾期 4.可返
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
     * 获取返款时间
     *
     * @return return_time - 返款时间
     */
    public String getReturnTime() {
        return returnTime;
    }

    /**
     * 设置返款时间
     *
     * @param returnTime 返款时间
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
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", presellReturnedRecordId=").append(presellReturnedRecordId);
        sb.append(", userId=").append(userId);
        sb.append(", returnedStatus=").append(returnedStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", returnMoney=").append(returnMoney);
        sb.append(", orderId=").append(orderId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}