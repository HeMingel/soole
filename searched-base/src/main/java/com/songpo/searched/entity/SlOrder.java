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
     * 订单编号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 用户唯一标识符
     */
    @Column(name = "user_id")
    private String userId;

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
     * 2：待支付 1：支付成功 0：支付失败 -1为已失效
     */
    @Column(name = "payment_state")
    private Integer paymentState;

    /**
     * 1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚
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
     * 记录状态 1:正常 0:删除
     */
    private Integer status;

    /**
     * 扣除的了豆总数
     */
    @Column(name = "deduct_total_pulse")
    private Integer deductTotalPulse;

    /**
     * 收件人姓名
     */
    @Column(name = "consigneeName")
    private String consigneename;

    /**
     * 收件人电话
     */
    @Column(name = "consigneePhone")
    private String consigneephone;

    /**
     * 收件人省地址
     */
    private String province;

    /**
     * 收件人市地址
     */
    private String city;

    /**
     * 收件人区地址
     */
    private String county;

    /**
     * 收件人详细地址
     */
    private String detailed;

    /**
     * -1:拼团/助力 失败 0:拼团/助力 中 1:拼团/助力 成功
     */
    @Column(name = "spell_group_status")
    private Integer spellGroupStatus;

    /**
     * 拼团团主
     */
    @Column(name = "group_master")
    private String groupMaster;

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
     * 获取订单编号
     *
     * @return serial_number - 订单编号
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置订单编号
     *
     * @param serialNumber 订单编号
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
     * 获取2：待支付 1：支付成功 0：支付失败 -1为已失效
     *
     * @return payment_state - 2：待支付 1：支付成功 0：支付失败 -1为已失效
     */
    public Integer getPaymentState() {
        return paymentState;
    }

    /**
     * 设置2：待支付 1：支付成功 0：支付失败 -1为已失效
     *
     * @param paymentState 2：待支付 1：支付成功 0：支付失败 -1为已失效
     */
    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    /**
     * 获取1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚
     *
     * @return type - 1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚
     *
     * @param type 1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚
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
     * 获取收件人姓名
     *
     * @return consigneeName - 收件人姓名
     */
    public String getConsigneename() {
        return consigneename;
    }

    /**
     * 设置收件人姓名
     *
     * @param consigneename 收件人姓名
     */
    public void setConsigneename(String consigneename) {
        this.consigneename = consigneename == null ? null : consigneename.trim();
    }

    /**
     * 获取收件人电话
     *
     * @return consigneePhone - 收件人电话
     */
    public String getConsigneephone() {
        return consigneephone;
    }

    /**
     * 设置收件人电话
     *
     * @param consigneephone 收件人电话
     */
    public void setConsigneephone(String consigneephone) {
        this.consigneephone = consigneephone == null ? null : consigneephone.trim();
    }

    /**
     * 获取收件人省地址
     *
     * @return province - 收件人省地址
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置收件人省地址
     *
     * @param province 收件人省地址
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取收件人市地址
     *
     * @return city - 收件人市地址
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置收件人市地址
     *
     * @param city 收件人市地址
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取收件人区地址
     *
     * @return county - 收件人区地址
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置收件人区地址
     *
     * @param county 收件人区地址
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * 获取收件人详细地址
     *
     * @return detailed - 收件人详细地址
     */
    public String getDetailed() {
        return detailed;
    }

    /**
     * 设置收件人详细地址
     *
     * @param detailed 收件人详细地址
     */
    public void setDetailed(String detailed) {
        this.detailed = detailed == null ? null : detailed.trim();
    }

    /**
     * 获取-1:拼团/助力 失败 0:拼团/助力 中 1:拼团/助力 成功
     *
     * @return spell_group_status - -1:拼团/助力 失败 0:拼团/助力 中 1:拼团/助力 成功
     */
    public Integer getSpellGroupStatus() {
        return spellGroupStatus;
    }

    /**
     * 设置-1:拼团/助力 失败 0:拼团/助力 中 1:拼团/助力 成功
     *
     * @param spellGroupStatus -1:拼团/助力 失败 0:拼团/助力 中 1:拼团/助力 成功
     */
    public void setSpellGroupStatus(Integer spellGroupStatus) {
        this.spellGroupStatus = spellGroupStatus;
    }

    /**
     * 获取拼团团主
     *
     * @return group_master - 拼团团主
     */
    public String getGroupMaster() {
        return groupMaster;
    }

    /**
     * 设置拼团团主
     *
     * @param groupMaster 拼团团主
     */
    public void setGroupMaster(String groupMaster) {
        this.groupMaster = groupMaster == null ? null : groupMaster.trim();
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
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", fee=").append(fee);
        sb.append(", paymentState=").append(paymentState);
        sb.append(", type=").append(type);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", shippingState=").append(shippingState);
        sb.append(", status=").append(status);
        sb.append(", deductTotalPulse=").append(deductTotalPulse);
        sb.append(", consigneename=").append(consigneename);
        sb.append(", consigneephone=").append(consigneephone);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", detailed=").append(detailed);
        sb.append(", spellGroupStatus=").append(spellGroupStatus);
        sb.append(", groupMaster=").append(groupMaster);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}