package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum BusinessApplicationTypeEnum implements BaseEnum {

    /**
     * 单播模式
     */
    PERSONAL("个人", 1),

    /**
     * 广播模式
     */
    ENTERPRISE("企业", 2);

    private String label;

    private Integer value;

    BusinessApplicationTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
