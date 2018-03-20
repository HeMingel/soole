package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_order")
public class SlOrder implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 编号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 用户唯一标识符
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 收货地址id
     */
    @Column(name = "shippng_address_id")
    private String shippngAddressId;

    /**
     * 订单总金额(可能是一个商品可能是多个商品)
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 0：待支付1：支付成功 2：支付失败
     */
    @Column(name = "payment_state")
    private Integer paymentState;

    /**
     * 1：普通订单2：拼团订单3:预售订单
     */
    private Integer type;

    /**
     * 1：微信支付
2：支付宝支付
3：厦门银行支付
     */
    @Column(name = "payment_channel")
    private Integer paymentChannel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private String payTime;

    /**
     * 0:待发货 1:已发货 2:已签收 3:已拒收
     */
    @Column(name = "shipping_state")
    private Integer shippingState;

    /**
     * 发货时间
     */
    @Column(name = "shipping_time")
    private String shippingTime;

    /**
     * 快递单号
     */
    @Column(name = "ship_number")
    private String shipNumber;

    /**
     * 记录状态 1:正常 0:删除
     */
    private Integer status;

    /**
     * 扣除的了豆总数
     */
    @Column(name = "deduct_total_pulse")
    private Integer deductTotalPulse;

    /**
     * 买家留言
     */
    @Column(name = "buyer_message")
    private String buyerMessage;

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
     * 获取编号
     *
     * @return serial_number - 编号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置编号
     *
     * @param serialNumber 编号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    /**
     * 获取用户唯一标识符
     *
     * @return user_id - 用户唯一标识符
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户唯一标识符
     *
     * @param userId 用户唯一标识符
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取收货地址id
     *
     * @return shippng_address_id - 收货地址id
     */
    public String getShippngAddressId() {
        return shippngAddressId;
    }

    /**
     * 设置收货地址id
     *
     * @param shippngAddressId 收货地址id
     */
    public void setShippngAddressId(String shippngAddressId) {
        this.shippngAddressId = shippngAddressId == null ? null : shippngAddressId.trim();
    }

    /**
     * 获取订单总金额(可能是一个商品可能是多个商品)
     *
     * @return total_amount - 订单总金额(可能是一个商品可能是多个商品)
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置订单总金额(可能是一个商品可能是多个商品)
     *
     * @param totalAmount 订单总金额(可能是一个商品可能是多个商品)
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取手续费
     *
     * @return fee - 手续费
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 设置手续费
     *
     * @param fee 手续费
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取0：待支付1：支付成功 2：支付失败
     *
     * @return payment_state - 0：待支付1：支付成功 2：支付失败
     */
    public Integer getPaymentState() {
        return paymentState;
    }

    /**
     * 设置0：待支付1：支付成功 2：支付失败
     *
     * @param paymentState 0：待支付1：支付成功 2：支付失败
     */
    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    /**
     * 获取1：普通订单2：拼团订单3:预售订单
     *
     * @return type - 1：普通订单2：拼团订单3:预售订单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：普通订单2：拼团订单3:预售订单
     *
     * @param type 1：普通订单2：拼团订单3:预售订单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取1：微信支付
2：支付宝支付
3：厦门银行支付
     *
     * @return payment_channel - 1：微信支付
2：支付宝支付
3：厦门银行支付
     */
    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    /**
     * 设置1：微信支付
2：支付宝支付
3：厦门银行支付
     *
     * @param paymentChannel 1：微信支付
2：支付宝支付
3：厦门银行支付
     */
    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
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
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    /**
     * 获取0:待发货 1:已发货 2:已签收 3:已拒收
     *
     * @return shipping_state - 0:待发货 1:已发货 2:已签收 3:已拒收
     */
    public Integer getShippingState() {
        return shippingState;
    }

    /**
     * 设置0:待发货 1:已发货 2:已签收 3:已拒收
     *
     * @param shippingState 0:待发货 1:已发货 2:已签收 3:已拒收
     */
    public void setShippingState(Integer shippingState) {
        this.shippingState = shippingState;
    }

    /**
     * 获取发货时间
     *
     * @return shipping_time - 发货时间
     */
    public String getShippingTime() {
        return shippingTime;
    }

    /**
     * 设置发货时间
     *
     * @param shippingTime 发货时间
     */
    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime == null ? null : shippingTime.trim();
    }

    /**
     * 获取快递单号
     *
     * @return ship_number - 快递单号
     */
    public String getShipNumber() {
        return shipNumber;
    }

    /**
     * 设置快递单号
     *
     * @param shipNumber 快递单号
     */
    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber == null ? null : shipNumber.trim();
    }

    /**
     * 获取记录状态 1:正常 0:删除
     *
     * @return status - 记录状态 1:正常 0:删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置记录状态 1:正常 0:删除
     *
     * @param status 记录状态 1:正常 0:删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取扣除的了豆总数
     *
     * @return deduct_total_pulse - 扣除的了豆总数
     */
    public Integer getDeductTotalPulse() {
        return deductTotalPulse;
    }

    /**
     * 设置扣除的了豆总数
     *
     * @param deductTotalPulse 扣除的了豆总数
     */
    public void setDeductTotalPulse(Integer deductTotalPulse) {
        this.deductTotalPulse = deductTotalPulse;
    }

    /**
     * 获取买家留言
     *
     * @return buyer_message - 买家留言
     */
    public String getBuyerMessage() {
        return buyerMessage;
    }

    /**
     * 设置买家留言
     *
     * @param buyerMessage 买家留言
     */
    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", userId=").append(userId);
        sb.append(", shippngAddressId=").append(shippngAddressId);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", fee=").append(fee);
        sb.append(", paymentState=").append(paymentState);
        sb.append(", type=").append(type);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", shippingState=").append(shippingState);
        sb.append(", shippingTime=").append(shippingTime);
        sb.append(", shipNumber=").append(shipNumber);
        sb.append(", status=").append(status);
        sb.append(", deductTotalPulse=").append(deductTotalPulse);
        sb.append(", buyerMessage=").append(buyerMessage);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}