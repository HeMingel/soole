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
     * 收货地址省地址
     */
    private String province;

    /**
     * 收货地址市地址
     */
    private String city;

    /**
     * 收货地址区地址
     */
    private String district;

    /**
     * 收货详细地址
     */
    private String address;

    /**
     * 总金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 0：待支付
                                            1：支付成功
                                            2：支付失败
     */
    @Column(name = "payment_state")
    private Integer paymentState;

    /**
     * 1：普通订单
                                  2：拼团订单
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
     * 获取收货地址省地址
     *
     * @return province - 收货地址省地址
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置收货地址省地址
     *
     * @param province 收货地址省地址
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取收货地址市地址
     *
     * @return city - 收货地址市地址
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置收货地址市地址
     *
     * @param city 收货地址市地址
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取收货地址区地址
     *
     * @return district - 收货地址区地址
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置收货地址区地址
     *
     * @param district 收货地址区地址
     */
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    /**
     * 获取收货详细地址
     *
     * @return address - 收货详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货详细地址
     *
     * @param address 收货详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取总金额
     *
     * @return total_amount - 总金额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置总金额
     *
     * @param totalAmount 总金额
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
     * 获取0：待支付
                                            1：支付成功
                                            2：支付失败
     *
     * @return payment_state - 0：待支付
                                            1：支付成功
                                            2：支付失败
     */
    public Integer getPaymentState() {
        return paymentState;
    }

    /**
     * 设置0：待支付
                                            1：支付成功
                                            2：支付失败
     *
     * @param paymentState 0：待支付
                                            1：支付成功
                                            2：支付失败
     */
    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    /**
     * 获取1：普通订单
                                  2：拼团订单
     *
     * @return type - 1：普通订单
                                  2：拼团订单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：普通订单
                                  2：拼团订单
     *
     * @param type 1：普通订单
                                  2：拼团订单
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", userId=").append(userId);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", district=").append(district);
        sb.append(", address=").append(address);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", fee=").append(fee);
        sb.append(", paymentState=").append(paymentState);
        sb.append(", type=").append(type);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}