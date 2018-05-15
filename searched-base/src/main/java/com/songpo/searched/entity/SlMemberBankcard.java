package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_member_bankcard")
public class SlMemberBankcard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 银行简码
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 银行卡号
     */
    @Column(name = "card_number")
    private Long cardNumber;

    /**
     * 持卡人姓名
     */
    private String cardholder;

    /**
     * 持卡人电话
     */
    @Column(name = "cardholder_phone")
    private String cardholderPhone;

    /**
     * 持卡人身份证号
     */
    @Column(name = "cardholder_identity_card")
    private String cardholderIdentityCard;

    /**
     * 开户行名称
     */
    @Column(name = "opening_bank")
    private String openingBank;

    /**
     * 所属银行
     */
    @Column(name = "affiliated_bank")
    private String affiliatedBank;

    /**
     * 卡类标识:1-储蓄卡；2-信用卡
     */
    @Column(name = "card_type")
    private Byte cardType;

    /**
     * 1.该卡为默认卡  2.该卡不是默认卡
     */
    @Column(name = "is_default")
    private Boolean isDefault;

    /**
     * 银行id
     */
    @Column(name = "bank_list_id")
    private String bankListId;

    /**
     * 银行签约号，有签约号可以不必传银行卡号等信息下单
     */
    @Column(name = "agree_no")
    private String agreeNo;

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
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * 获取银行名称
     *
     * @return bank_name - 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 获取银行简码
     *
     * @return bank_code - 银行简码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置银行简码
     *
     * @param bankCode 银行简码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    /**
     * 获取银行卡号
     *
     * @return card_number - 银行卡号
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置银行卡号
     *
     * @param cardNumber 银行卡号
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * 获取持卡人姓名
     *
     * @return cardholder - 持卡人姓名
     */
    public String getCardholder() {
        return cardholder;
    }

    /**
     * 设置持卡人姓名
     *
     * @param cardholder 持卡人姓名
     */
    public void setCardholder(String cardholder) {
        this.cardholder = cardholder == null ? null : cardholder.trim();
    }

    /**
     * 获取持卡人电话
     *
     * @return cardholder_phone - 持卡人电话
     */
    public String getCardholderPhone() {
        return cardholderPhone;
    }

    /**
     * 设置持卡人电话
     *
     * @param cardholderPhone 持卡人电话
     */
    public void setCardholderPhone(String cardholderPhone) {
        this.cardholderPhone = cardholderPhone == null ? null : cardholderPhone.trim();
    }

    /**
     * 获取持卡人身份证号
     *
     * @return cardholder_identity_card - 持卡人身份证号
     */
    public String getCardholderIdentityCard() {
        return cardholderIdentityCard;
    }

    /**
     * 设置持卡人身份证号
     *
     * @param cardholderIdentityCard 持卡人身份证号
     */
    public void setCardholderIdentityCard(String cardholderIdentityCard) {
        this.cardholderIdentityCard = cardholderIdentityCard == null ? null : cardholderIdentityCard.trim();
    }

    /**
     * 获取开户行名称
     *
     * @return opening_bank - 开户行名称
     */
    public String getOpeningBank() {
        return openingBank;
    }

    /**
     * 设置开户行名称
     *
     * @param openingBank 开户行名称
     */
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank == null ? null : openingBank.trim();
    }

    /**
     * 获取所属银行
     *
     * @return affiliated_bank - 所属银行
     */
    public String getAffiliatedBank() {
        return affiliatedBank;
    }

    /**
     * 设置所属银行
     *
     * @param affiliatedBank 所属银行
     */
    public void setAffiliatedBank(String affiliatedBank) {
        this.affiliatedBank = affiliatedBank == null ? null : affiliatedBank.trim();
    }

    /**
     * 获取卡类标识:1-储蓄卡；2-信用卡
     *
     * @return card_type - 卡类标识:1-储蓄卡；2-信用卡
     */
    public Byte getCardType() {
        return cardType;
    }

    /**
     * 设置卡类标识:1-储蓄卡；2-信用卡
     *
     * @param cardType 卡类标识:1-储蓄卡；2-信用卡
     */
    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取1.该卡为默认卡  2.该卡不是默认卡
     *
     * @return is_default - 1.该卡为默认卡  2.该卡不是默认卡
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 设置1.该卡为默认卡  2.该卡不是默认卡
     *
     * @param isDefault 1.该卡为默认卡  2.该卡不是默认卡
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取银行id
     *
     * @return bank_list_id - 银行id
     */
    public String getBankListId() {
        return bankListId;
    }

    /**
     * 设置银行id
     *
     * @param bankListId 银行id
     */
    public void setBankListId(String bankListId) {
        this.bankListId = bankListId == null ? null : bankListId.trim();
    }

    /**
     * 获取银行签约号，有签约号可以不必传银行卡号等信息下单
     *
     * @return agree_no - 银行签约号，有签约号可以不必传银行卡号等信息下单
     */
    public String getAgreeNo() {
        return agreeNo;
    }

    /**
     * 设置银行签约号，有签约号可以不必传银行卡号等信息下单
     *
     * @param agreeNo 银行签约号，有签约号可以不必传银行卡号等信息下单
     */
    public void setAgreeNo(String agreeNo) {
        this.agreeNo = agreeNo == null ? null : agreeNo.trim();
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
        sb.append(", userId=").append(userId);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankCode=").append(bankCode);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", cardholder=").append(cardholder);
        sb.append(", cardholderPhone=").append(cardholderPhone);
        sb.append(", cardholderIdentityCard=").append(cardholderIdentityCard);
        sb.append(", openingBank=").append(openingBank);
        sb.append(", affiliatedBank=").append(affiliatedBank);
        sb.append(", cardType=").append(cardType);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", bankListId=").append(bankListId);
        sb.append(", agreeNo=").append(agreeNo);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}