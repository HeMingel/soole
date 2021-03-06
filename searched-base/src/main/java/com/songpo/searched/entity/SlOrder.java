package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
     * 扣除的了豆总数
     */
    @Column(name = "deduct_total_pulse")
    private Integer deductTotalPulse;

    /**
     * 2：待支付 1：支付成功 0：支付失败 101为已失效 102已取消 103拍卖退款
     */
    @Column(name = "payment_state")
    private Integer paymentState;

    /**
     * 1：微信支付
2：支付宝支付 3：了豆支付
     */
    @Column(name = "payment_channel")
    private Integer paymentChannel;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private String payTime;

    /**
     * 支付时间戳
     */
    @Column(name = "pay_time_stamp")
    private Long payTimeStamp;

    /**
     * 记录状态 1:正常 0:删除
     */
    private Integer status;

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
     * 0:拼团/助力 失败 1:拼团/助力 中 2:拼团/助力 成功
     */
    @Column(name = "spell_group_status")
    private Integer spellGroupStatus;

    /**
     * 拼团团主
     */
    @Column(name = "group_master")
    private String groupMaster;

    /**
     * 分享次数
     */
    @Column(name = "share_count")
    private Integer shareCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * (暂未使用)商品活动id
     */
    @Column(name = "activity_product_id")
    private String activityProductId;

    /**
     * (暂未使用)创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * (暂未使用)备注
     */
    private String remark;

    /**
     * (暂未使用)1：普通订单 2：拼团订单 3:预售订单 4:助力购 5:消费奖励 6:豆赚
     */
    private Integer type;

    /**
     * (暂未使用)手续费
     */
    private BigDecimal fee;

    /**
     * 是否虚拟用户开团: 1 不是   2 是
     */
    @Column(name = "virtual_open")
    private Integer virtualOpen;

    /**
     * 商户订单号(拍卖新字段)
     */
    @Column(name = "out_order_number")
    private String outOrderNumber;

    /**
     * 订单银豆总额(拍卖新字段)
     */
    @Column(name = "total_bean")
    private Integer totalBean;

    /**
     * 支付方式(关联base_payment表ID)(拍卖新字段)
     */
    @Column(name = "bp_id")
    private String bpId;

    /**
     * 收货地址ID(拍卖新字段)
     */
    @Column(name = "addr_id")
    private String addrId;

    /**
     * 虚拟删除   1正常(默认)  2虚拟删除(拍卖新字段)
     */
    @Column(name = "del_status")
    private Integer delStatus;

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
     * 获取2：待支付 1：支付成功 0：支付失败 101为已失效 102已取消 103拍卖退款
     *
     * @return payment_state - 2：待支付 1：支付成功 0：支付失败 101为已失效 102已取消 103拍卖退款
     */
    public Integer getPaymentState() {
        return paymentState;
    }

    /**
     * 设置2：待支付 1：支付成功 0：支付失败 101为已失效 102已取消 103拍卖退款
     *
     * @param paymentState 2：待支付 1：支付成功 0：支付失败 101为已失效 102已取消 103拍卖退款
     */
    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    /**
     * 获取1：微信支付
2：支付宝支付 3：了豆支付
     *
     * @return payment_channel - 1：微信支付
2：支付宝支付 3：了豆支付
     */
    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    /**
     * 设置1：微信支付
2：支付宝支付 3：了豆支付
     *
     * @param paymentChannel 1：微信支付
2：支付宝支付 3：了豆支付
     */
    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
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
     * 获取支付时间戳
     *
     * @return pay_time_stamp - 支付时间戳
     */
    public Long getPayTimeStamp() {
        return payTimeStamp;
    }

    /**
     * 设置支付时间戳
     *
     * @param payTimeStamp 支付时间戳
     */
    public void setPayTimeStamp(Long payTimeStamp) {
        this.payTimeStamp = payTimeStamp;
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
     * 获取0:拼团/助力 失败 1:拼团/助力 中 2:拼团/助力 成功
     *
     * @return spell_group_status - 0:拼团/助力 失败 1:拼团/助力 中 2:拼团/助力 成功
     */
    public Integer getSpellGroupStatus() {
        return spellGroupStatus;
    }

    /**
     * 设置0:拼团/助力 失败 1:拼团/助力 中 2:拼团/助力 成功
     *
     * @param spellGroupStatus 0:拼团/助力 失败 1:拼团/助力 中 2:拼团/助力 成功
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

    /**
     * 获取分享次数
     *
     * @return share_count - 分享次数
     */
    public Integer getShareCount() {
        return shareCount;
    }

    /**
     * 设置分享次数
     *
     * @param shareCount 分享次数
     */
    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
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

    /**
     * 获取(暂未使用)商品活动id
     *
     * @return activity_product_id - (暂未使用)商品活动id
     */
    public String getActivityProductId() {
        return activityProductId;
    }

    /**
     * 设置(暂未使用)商品活动id
     *
     * @param activityProductId (暂未使用)商品活动id
     */
    public void setActivityProductId(String activityProductId) {
        this.activityProductId = activityProductId == null ? null : activityProductId.trim();
    }

    /**
     * 获取(暂未使用)创建时间
     *
     * @return created_at - (暂未使用)创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置(暂未使用)创建时间
     *
     * @param createdAt (暂未使用)创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取(暂未使用)备注
     *
     * @return remark - (暂未使用)备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置(暂未使用)备注
     *
     * @param remark (暂未使用)备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取(暂未使用)1：普通订单 2：拼团订单 3:预售订单 4:助力购 5:消费奖励 6:豆赚
     *
     * @return type - (暂未使用)1：普通订单 2：拼团订单 3:预售订单 4:助力购 5:消费奖励 6:豆赚
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置(暂未使用)1：普通订单 2：拼团订单 3:预售订单 4:助力购 5:消费奖励 6:豆赚
     *
     * @param type (暂未使用)1：普通订单 2：拼团订单 3:预售订单 4:助力购 5:消费奖励 6:豆赚
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取(暂未使用)手续费
     *
     * @return fee - (暂未使用)手续费
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 设置(暂未使用)手续费
     *
     * @param fee (暂未使用)手续费
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取是否虚拟用户开团: 1 不是   2 是
     *
     * @return virtual_open - 是否虚拟用户开团: 1 不是   2 是
     */
    public Integer getVirtualOpen() {
        return virtualOpen;
    }

    /**
     * 设置是否虚拟用户开团: 1 不是   2 是
     *
     * @param virtualOpen 是否虚拟用户开团: 1 不是   2 是
     */
    public void setVirtualOpen(Integer virtualOpen) {
        this.virtualOpen = virtualOpen;
    }

    /**
     * 获取商户订单号(拍卖新字段)
     *
     * @return out_order_number - 商户订单号(拍卖新字段)
     */
    public String getOutOrderNumber() {
        return outOrderNumber;
    }

    /**
     * 设置商户订单号(拍卖新字段)
     *
     * @param outOrderNumber 商户订单号(拍卖新字段)
     */
    public void setOutOrderNumber(String outOrderNumber) {
        this.outOrderNumber = outOrderNumber == null ? null : outOrderNumber.trim();
    }

    /**
     * 获取订单银豆总额(拍卖新字段)
     *
     * @return total_bean - 订单银豆总额(拍卖新字段)
     */
    public Integer getTotalBean() {
        return totalBean;
    }

    /**
     * 设置订单银豆总额(拍卖新字段)
     *
     * @param totalBean 订单银豆总额(拍卖新字段)
     */
    public void setTotalBean(Integer totalBean) {
        this.totalBean = totalBean;
    }

    /**
     * 获取支付方式(关联base_payment表ID)(拍卖新字段)
     *
     * @return bp_id - 支付方式(关联base_payment表ID)(拍卖新字段)
     */
    public String getBpId() {
        return bpId;
    }

    /**
     * 设置支付方式(关联base_payment表ID)(拍卖新字段)
     *
     * @param bpId 支付方式(关联base_payment表ID)(拍卖新字段)
     */
    public void setBpId(String bpId) {
        this.bpId = bpId == null ? null : bpId.trim();
    }

    /**
     * 获取收货地址ID(拍卖新字段)
     *
     * @return addr_id - 收货地址ID(拍卖新字段)
     */
    public String getAddrId() {
        return addrId;
    }

    /**
     * 设置收货地址ID(拍卖新字段)
     *
     * @param addrId 收货地址ID(拍卖新字段)
     */
    public void setAddrId(String addrId) {
        this.addrId = addrId == null ? null : addrId.trim();
    }

    /**
     * 获取虚拟删除   1正常(默认)  2虚拟删除(拍卖新字段)
     *
     * @return del_status - 虚拟删除   1正常(默认)  2虚拟删除(拍卖新字段)
     */
    public Integer getDelStatus() {
        return delStatus;
    }

    /**
     * 设置虚拟删除   1正常(默认)  2虚拟删除(拍卖新字段)
     *
     * @param delStatus 虚拟删除   1正常(默认)  2虚拟删除(拍卖新字段)
     */
    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
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
        sb.append(", deductTotalPulse=").append(deductTotalPulse);
        sb.append(", paymentState=").append(paymentState);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", payTime=").append(payTime);
        sb.append(", payTimeStamp=").append(payTimeStamp);
        sb.append(", status=").append(status);
        sb.append(", consigneename=").append(consigneename);
        sb.append(", consigneephone=").append(consigneephone);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", detailed=").append(detailed);
        sb.append(", spellGroupStatus=").append(spellGroupStatus);
        sb.append(", groupMaster=").append(groupMaster);
        sb.append(", shareCount=").append(shareCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", activityProductId=").append(activityProductId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", remark=").append(remark);
        sb.append(", type=").append(type);
        sb.append(", fee=").append(fee);
        sb.append(", virtualOpen=").append(virtualOpen);
        sb.append(", outOrderNumber=").append(outOrderNumber);
        sb.append(", totalBean=").append(totalBean);
        sb.append(", bpId=").append(bpId);
        sb.append(", addrId=").append(addrId);
        sb.append(", delStatus=").append(delStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}