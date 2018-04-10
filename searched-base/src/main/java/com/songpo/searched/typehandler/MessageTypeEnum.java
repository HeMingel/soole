package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum MessageTypeEnum implements BaseEnum {

    /**
     * 系统通知
     */
    SYSTEM("系统通知", 1),

    /**
     * 商品通知类型
     */
    PRODUCT_BUY("商品购买通知", 21),
    PRODUCT_SHELF("商品上架通知", 22),
    PRODUCT_DROPOFF("商品下架通知", 23),

    /**
     * 订单通知类型
     */
    ORDER_PRECREATE("订单预创建通知", 31),
    ORDER_CANCLE("订单取消通知", 32),

    /**
     * 订单通知类型
     */
    PAY_WAIT("支付等待通知", 41),
    PAY_SUCCESS("支付成功通知", 42),
    PAY_FAILD("支付失败通知", 43),
    PAY_CANCLE("支付取消通知", 44),

    /**
     * 配送通知类型
     */
    SHIPPING_WAIT("配送等待通知", 51),
    SHIPPING_SUCCESS("配送成功通知", 52),
    SHIPPING_FAILD("配送失败通知", 53),
    SHIPPING_CANCLE("配送取消通知", 54);

    private String label;

    private Integer value;

    MessageTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
