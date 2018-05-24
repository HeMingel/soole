package com.songpo.searched.typehandler;

/**
 * 交易类型枚举
 * sl_transaction_detail.transaction_type
 */
public enum TransactionTypeEnum implements BaseEnum {

    EXPENDITURE("支出", 1),
    INCOME("收入", 2);

    private String label;

    private Integer value;

    TransactionTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
