package com.songpo.searched.domain;

public enum CmMessageTypeEnum {

    /**
     * 单播类型
     */
    QUEUE("单播", 1),

    /**
     * 广播类型
     */
    TOPIC("广播", 2);

    String name;
    Integer value;


    CmMessageTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
