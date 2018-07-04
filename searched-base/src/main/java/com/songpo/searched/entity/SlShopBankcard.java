package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_shop_bankcard")
public class SlShopBankcard implements Serializable {
    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 持卡人姓名
     */
    private String cardholder;

    /**
     * 银行卡号
     */
    @Column(name = "card_number")
    private Long cardNumber;

    /**
     * 开户行
     */
    @Column(name = "opening_bank")
    private String openingBank;

    private static final long serialVersionUID = 1L;

    /**
     * 获取编号
     *
     * @return id - 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取商户id
     *
     * @return user_id - 商户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置商户id
     *
     * @param userId 商户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
     * 获取开户行
     *
     * @return opening_bank - 开户行
     */
    public String getOpeningBank() {
        return openingBank;
    }

    /**
     * 设置开户行
     *
     * @param openingBank 开户行
     */
    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank == null ? null : openingBank.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", cardholder=").append(cardholder);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", openingBank=").append(openingBank);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}