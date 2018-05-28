package com.songpo.searched.typehandler;

/**
 * 消费方式枚举
 * sl_transaction_detail.type
 */
public enum ExpenditureTypeEnum implements BaseEnum {

    /**
     * （1-99：红包、转账业务）
     * （100-199：活动相关）
     * （200-299：购物相关）
     * （300-400：收益相关）
     */
    TRANSFER_ACCOUNTS("转账", 1),
    ACCEPT_TRANSFER("接收转账", 2),
    GIVE_RED_ENVELOPE("发红包", 3),
    SNATCH_RED_ENVELOPE("抢红包", 4),
    BACK_RED_ENVELOPE("红包过期退回", 5),
    DEPOSIT_WITHDRAW("余额提现", 6),
    DEPOSIT_RECHARGE("余额充值", 8),
    NEW_GIFT_BAG("新人礼包（平台赠送）", 100),
    SIGN_IN_REWARD("签到奖励", 101),
    INVITE_REWARD("邀请好友奖励", 102),
    SHOPPING_PAYMENT("购物支付", 200),
    SHOPPING_AWAY("购物赠送", 201),
    COMMENT_REWARD("评价晒单奖励", 202),
    SHARE_AWARD("分享奖励", 203);

    private String label;

    private Integer value;

    ExpenditureTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
