package com.songpo.searched.typehandler;

/**
 * 订单类型枚举
 */
public enum OrderDetailTypeEnum implements BaseEnum {

    NORMAL_ORDER("（普通商品）普通订单", 1),
    GROUP_ORDER("（人气拼团）拼团订单", 2),
    PRESELL_ORDER("（云易购物）预售订单", 3),
    ONE_ORDER("（助力购物）助力购", 4),
    REBATE_ORDER("（广告赠送）消费奖励", 5),
    BEANS_ORDER("（优惠购物）豆赚", 6);


    private String label;

    private Integer value;

    OrderDetailTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    /**
     * 根据value获取实例
     *
     * @param value
     * @return
     */
    public static OrderDetailTypeEnum getInstance(Integer value) {
        for (OrderDetailTypeEnum currEnum : OrderDetailTypeEnum.values()) {
            if (currEnum.getValue() == value) {
                return currEnum;
            }
        }
        return null;
    }
}
