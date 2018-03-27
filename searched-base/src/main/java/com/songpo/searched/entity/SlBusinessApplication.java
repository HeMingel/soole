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
     * 公司地址
     */
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 员工数量
     */
    @Column(name = "employee_count")
    private String employeeCount;

    /**
     * 企业需要清仓的原因
     */
    @Column(name = "reasons_for_clearance")
    private String reasonsForClearance;

    /**
     * 企业需要清仓的产品
     */
    @Column(name = "products_for_clearance")
    private String productsForClearance;

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
     * 店铺申请时间
     */
    @Column(name = "business_create_time")
    private String businessCreateTime;

    /**
     * 店铺审核时间
     */
    @Column(name = "business_check_time")
    private String businessCheckTime;

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
     * 获取公司地址
     *
     * @return company_address - 公司地址
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 设置公司地址
     *
     * @param companyAddress 公司地址
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
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
     * 获取员工数量
     *
     * @return employee_count - 员工数量
     */
    public String getEmployeeCount() {
        return employeeCount;
    }

    /**
     * 设置员工数量
     *
     * @param employeeCount 员工数量
     */
    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount == null ? null : employeeCount.trim();
    }

    /**
     * 获取企业需要清仓的原因
     *
     * @return reasons_for_clearance - 企业需要清仓的原因
     */
    public String getReasonsForClearance() {
        return reasonsForClearance;
    }

    /**
     * 设置企业需要清仓的原因
     *
     * @param reasonsForClearance 企业需要清仓的原因
     */
    public void setReasonsForClearance(String reasonsForClearance) {
        this.reasonsForClearance = reasonsForClearance == null ? null : reasonsForClearance.trim();
    }

    /**
     * 获取企业需要清仓的产品
     *
     * @return products_for_clearance - 企业需要清仓的产品
     */
    public String getProductsForClearance() {
        return productsForClearance;
    }

    /**
     * 设置企业需要清仓的产品
     *
     * @param productsForClearance 企业需要清仓的产品
     */
    public void setProductsForClearance(String productsForClearance) {
        this.productsForClearance = productsForClearance == null ? null : productsForClearance.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", productTypeId=").append(productTypeId);
        sb.append(", companyAddress=").append(companyAddress);
        sb.append(", companyName=").append(companyName);
        sb.append(", employeeCount=").append(employeeCount);
        sb.append(", reasonsForClearance=").append(reasonsForClearance);
        sb.append(", productsForClearance=").append(productsForClearance);
        sb.append(", realName=").append(realName);
        sb.append(", phone=").append(phone);
        sb.append(", idCardFrontImageUrl=").append(idCardFrontImageUrl);
        sb.append(", idCardBackImageUrl=").append(idCardBackImageUrl);
        sb.append(", idCardHandImageUrl=").append(idCardHandImageUrl);
        sb.append(", businessCheckState=").append(businessCheckState);
        sb.append(", businessCreateTime=").append(businessCreateTime);
        sb.append(", businessCheckTime=").append(businessCheckTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}