package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_business_application")
public class SlBusinessApplication implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 店铺申请人ID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 经营类目
     */
    @Column(name = "product_type_id")
    private String productTypeId;

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
    private String country;

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
     * 申请类型：1 个人、2 企业
     */
    private Integer type;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

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
     * 店铺审核状态1待审核2已通过3已拒绝
     */
    @Column(name = "business_check_state")
    private Byte businessCheckState;

    /**
     * 营业执照
     */
    @Column(name = "business_image_url")
    private String businessImageUrl;

    /**
     * 店铺申请时间
     */
    @Column(name = "business_create_time")
    private String businessCreateTime;

    /**
     * 店铺审核时间
     */
    @Column(name = "business_check_time")
    private String businessCheckTime;

    /**
     * 账号状态：0 禁用， 1 启用
     */
    private Integer status;

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
     * 获取店铺申请人ID
     *
     * @return user_id - 店铺申请人ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置店铺申请人ID
     *
     * @param userId 店铺申请人ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取经营类目
     *
     * @return product_type_id - 经营类目
     */
    public String getProductTypeId() {
        return productTypeId;
    }

    /**
     * 设置经营类目
     *
     * @param productTypeId 经营类目
     */
    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
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
     * @return country - 区县
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置区县
     *
     * @param country 区县
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
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
     * 获取申请类型：1 个人、2 企业
     *
     * @return type - 申请类型：1 个人、2 企业
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置申请类型：1 个人、2 企业
     *
     * @param type 申请类型：1 个人、2 企业
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取公司名称
     *
     * @return company_name - 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称
     *
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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
     * 获取店铺审核状态1待审核2已通过3已拒绝
     *
     * @return business_check_state - 店铺审核状态1待审核2已通过3已拒绝
     */
    public Byte getBusinessCheckState() {
        return businessCheckState;
    }

    /**
     * 设置店铺审核状态1待审核2已通过3已拒绝
     *
     * @param businessCheckState 店铺审核状态1待审核2已通过3已拒绝
     */
    public void setBusinessCheckState(Byte businessCheckState) {
        this.businessCheckState = businessCheckState;
    }

    /**
     * 获取营业执照
     *
     * @return business_image_url - 营业执照
     */
    public String getBusinessImageUrl() {
        return businessImageUrl;
    }

    /**
     * 设置营业执照
     *
     * @param businessImageUrl 营业执照
     */
    public void setBusinessImageUrl(String businessImageUrl) {
        this.businessImageUrl = businessImageUrl == null ? null : businessImageUrl.trim();
    }

    /**
     * 获取店铺申请时间
     *
     * @return business_create_time - 店铺申请时间
     */
    public String getBusinessCreateTime() {
        return businessCreateTime;
    }

    /**
     * 设置店铺申请时间
     *
     * @param businessCreateTime 店铺申请时间
     */
    public void setBusinessCreateTime(String businessCreateTime) {
        this.businessCreateTime = businessCreateTime == null ? null : businessCreateTime.trim();
    }

    /**
     * 获取店铺审核时间
     *
     * @return business_check_time - 店铺审核时间
     */
    public String getBusinessCheckTime() {
        return businessCheckTime;
    }

    /**
     * 设置店铺审核时间
     *
     * @param businessCheckTime 店铺审核时间
     */
    public void setBusinessCheckTime(String businessCheckTime) {
        this.businessCheckTime = businessCheckTime == null ? null : businessCheckTime.trim();
    }

    /**
     * 获取账号状态：0 禁用， 1 启用
     *
     * @return status - 账号状态：0 禁用， 1 启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置账号状态：0 禁用， 1 启用
     *
     * @param status 账号状态：0 禁用， 1 启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", country=").append(country);
        sb.append(", town=").append(town);
        sb.append(", street=").append(street);
        sb.append(", otherAddress=").append(otherAddress);
        sb.append(", type=").append(type);
        sb.append(", realName=").append(realName);
        sb.append(", companyName=").append(companyName);
        sb.append(", phone=").append(phone);
        sb.append(", idCardNumber=").append(idCardNumber);
        sb.append(", idCardFrontImageUrl=").append(idCardFrontImageUrl);
        sb.append(", idCardBackImageUrl=").append(idCardBackImageUrl);
        sb.append(", idCardHandImageUrl=").append(idCardHandImageUrl);
        sb.append(", businessCheckState=").append(businessCheckState);
        sb.append(", businessImageUrl=").append(businessImageUrl);
        sb.append(", businessCreateTime=").append(businessCreateTime);
        sb.append(", businessCheckTime=").append(businessCheckTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}