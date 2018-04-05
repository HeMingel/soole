package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_agent_application")
public class SlAgentApplication implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 代理商用户ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

    /**
     * 乡镇
     */
    private String town;

    /**
     * 村、街道
     */
    private String street;

    /**
     * 剩余地址信息
     */
    @Column(name = "other_address")
    private String otherAddress;

    /**
     * 申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商
     */
    private Integer level;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 身份证号码
     */
    @Column(name = "id_card_number")
    private String idCardNumber;

    /**
     * 身份证正面
     */
    @Column(name = "id_card_front_image_url")
    private String idCardFrontImageUrl;

    /**
     * 身份证反面
     */
    @Column(name = "id_card_back_image_url")
    private String idCardBackImageUrl;

    /**
     * 手持身份证
     */
    @Column(name = "id_card_hand_image_url")
    private String idCardHandImageUrl;

    /**
     * 代理商审核状态1待审核2已通过3已拒绝
     */
    @Column(name = "agent_check_state")
    private Byte agentCheckState;

    /**
     * 代理商申请时间
     */
    @Column(name = "agent_create_time")
    private String agentCreateTime;

    /**
     * 代理商审核时间
     */
    @Column(name = "agent_check_time")
    private String agentCheckTime;

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
     * 获取代理商用户ID
     *
     * @return user_id - 代理商用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置代理商用户ID
     *
     * @param userId 代理商用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取区县
     *
     * @return county - 区县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置区县
     *
     * @param county 区县
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * 获取乡镇
     *
     * @return town - 乡镇
     */
    public String getTown() {
        return town;
    }

    /**
     * 设置乡镇
     *
     * @param town 乡镇
     */
    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    /**
     * 获取村、街道
     *
     * @return street - 村、街道
     */
    public String getStreet() {
        return street;
    }

    /**
     * 设置村、街道
     *
     * @param street 村、街道
     */
    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    /**
     * 获取剩余地址信息
     *
     * @return other_address - 剩余地址信息
     */
    public String getOtherAddress() {
        return otherAddress;
    }

    /**
     * 设置剩余地址信息
     *
     * @param otherAddress 剩余地址信息
     */
    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress == null ? null : otherAddress.trim();
    }

    /**
     * 获取申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商
     *
     * @return level - 申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商
     *
     * @param level 申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取姓名
     *
     * @return real_name - 姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置姓名
     *
     * @param realName 姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取身份证号码
     *
     * @return id_card_number - 身份证号码
     */
    public String getIdCardNumber() {
        return idCardNumber;
    }

    /**
     * 设置身份证号码
     *
     * @param idCardNumber 身份证号码
     */
    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber == null ? null : idCardNumber.trim();
    }

    /**
     * 获取身份证正面
     *
     * @return id_card_front_image_url - 身份证正面
     */
    public String getIdCardFrontImageUrl() {
        return idCardFrontImageUrl;
    }

    /**
     * 设置身份证正面
     *
     * @param idCardFrontImageUrl 身份证正面
     */
    public void setIdCardFrontImageUrl(String idCardFrontImageUrl) {
        this.idCardFrontImageUrl = idCardFrontImageUrl == null ? null : idCardFrontImageUrl.trim();
    }

    /**
     * 获取身份证反面
     *
     * @return id_card_back_image_url - 身份证反面
     */
    public String getIdCardBackImageUrl() {
        return idCardBackImageUrl;
    }

    /**
     * 设置身份证反面
     *
     * @param idCardBackImageUrl 身份证反面
     */
    public void setIdCardBackImageUrl(String idCardBackImageUrl) {
        this.idCardBackImageUrl = idCardBackImageUrl == null ? null : idCardBackImageUrl.trim();
    }

    /**
     * 获取手持身份证
     *
     * @return id_card_hand_image_url - 手持身份证
     */
    public String getIdCardHandImageUrl() {
        return idCardHandImageUrl;
    }

    /**
     * 设置手持身份证
     *
     * @param idCardHandImageUrl 手持身份证
     */
    public void setIdCardHandImageUrl(String idCardHandImageUrl) {
        this.idCardHandImageUrl = idCardHandImageUrl == null ? null : idCardHandImageUrl.trim();
    }

    /**
     * 获取代理商审核状态1待审核2已通过3已拒绝
     *
     * @return agent_check_state - 代理商审核状态1待审核2已通过3已拒绝
     */
    public Byte getAgentCheckState() {
        return agentCheckState;
    }

    /**
     * 设置代理商审核状态1待审核2已通过3已拒绝
     *
     * @param agentCheckState 代理商审核状态1待审核2已通过3已拒绝
     */
    public void setAgentCheckState(Byte agentCheckState) {
        this.agentCheckState = agentCheckState;
    }

    /**
     * 获取代理商申请时间
     *
     * @return agent_create_time - 代理商申请时间
     */
    public String getAgentCreateTime() {
        return agentCreateTime;
    }

    /**
     * 设置代理商申请时间
     *
     * @param agentCreateTime 代理商申请时间
     */
    public void setAgentCreateTime(String agentCreateTime) {
        this.agentCreateTime = agentCreateTime == null ? null : agentCreateTime.trim();
    }

    /**
     * 获取代理商审核时间
     *
     * @return agent_check_time - 代理商审核时间
     */
    public String getAgentCheckTime() {
        return agentCheckTime;
    }

    /**
     * 设置代理商审核时间
     *
     * @param agentCheckTime 代理商审核时间
     */
    public void setAgentCheckTime(String agentCheckTime) {
        this.agentCheckTime = agentCheckTime == null ? null : agentCheckTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", town=").append(town);
        sb.append(", street=").append(street);
        sb.append(", otherAddress=").append(otherAddress);
        sb.append(", level=").append(level);
        sb.append(", realName=").append(realName);
        sb.append(", phone=").append(phone);
        sb.append(", idCardNumber=").append(idCardNumber);
        sb.append(", idCardFrontImageUrl=").append(idCardFrontImageUrl);
        sb.append(", idCardBackImageUrl=").append(idCardBackImageUrl);
        sb.append(", idCardHandImageUrl=").append(idCardHandImageUrl);
        sb.append(", agentCheckState=").append(agentCheckState);
        sb.append(", agentCreateTime=").append(agentCreateTime);
        sb.append(", agentCheckTime=").append(agentCheckTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}