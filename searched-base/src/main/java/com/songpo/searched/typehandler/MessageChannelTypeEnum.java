package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum MessageChannelTypeEnum implements BaseEnum {

    /**
     * 单播模式
     */
    QUEUE("单播", 1),

    /**
     * 广播模式
     */
    TOPIC("广播", 2);

    private String label;

    private Integer value;

    MessageChannelTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
