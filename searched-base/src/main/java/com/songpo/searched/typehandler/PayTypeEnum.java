package com.songpo.searched.typehandler;

/**
 * 支付类型枚举
 */
public enum PayTypeEnum implements BaseEnum {

    WXPAY("微信支付", 1),
    ALIPAY("支付宝支付", 2),
    BANK_CARD_PAY("银行卡支付", 3);

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
