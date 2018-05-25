package com.songpo.searched.typehandler;

/**
 * 支付类型枚举
 */
public enum PayTypeEnum implements BaseEnum {

    WX_APP_PAY("微信App支付", 1),
    WX_H5_PAY("微信H5支付", 2),
    ALI_APP_PAY("支付宝App支付", 3),
    ALI_H5_PAY("支付宝H5支付", 4),
    BANK_CARD_PAY("银行卡支付", 5);

    private String label;

    private Integer value;

    PayTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    /**
     * 根据value获取实例
     *
     * @param value
     * @return
     */
    public static PayTypeEnum getInstance(Integer value) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getValue() == value) {
                return payTypeEnum;
            }
        }
        return null;
    }


}
