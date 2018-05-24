package com.songpo.searched.typehandler;

/**
 * 交易货币类型枚举
 * sl_transaction_detail.deal_type
 */
public enum TransactionCurrencyTypeEnum implements BaseEnum {
    ACCOUNT_BALANCE("账户余额", 1),
    MONEY("钱", 3),
    COIN("金豆", 5),
    SILVER("银豆", 6);

    private String label;

    private Integer value;

    TransactionCurrencyTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
