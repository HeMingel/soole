package com.songpo.searched.typehandler;

/**
 * 交易状态枚举
 * sl_transaction_detail.transaction_status
 */
public enum TransactionStatusEnum implements BaseEnum {

    EFFICIENT("已生效", 1),
    INEFFECTIVE("未生效", 2);

    private String label;

    private Integer value;

    TransactionStatusEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
