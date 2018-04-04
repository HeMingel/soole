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
     * 销售模式
     */
    SALES_MODE_NORMAL("普通商品购买通知", 21),
    SALES_MODE_TEAMWORK("人气拼团商品购买通知", 22),
    SALES_MODE_PRESALE("预售商品购买通知", 23),
    SALES_MODE_BEAN("豆赚商品购买通知", 24),
    SALES_MODE_REBATE("消费返利商品购买通知", 25),
    SALES_MODE_FREEBUY("随心购商品购买通知", 26),

    /**
     * 促销活动
     */
    ACTIVITY_NORMAL("无活动商品购买通知", 31),
    ACTIVITY_NEWMEMBER("新人专享商品购买通知", 32),
    ACTIVITY_RECOMMENDED_REWARD("推荐奖励商品购买通知", 33),
    ACTIVITY_LIMITED_TIME_SPIKE("限时秒杀商品购买通知", 34);

    private String label;

    private Integer value;

    MessageTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
