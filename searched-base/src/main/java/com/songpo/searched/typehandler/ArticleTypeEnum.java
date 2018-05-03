package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum ArticleTypeEnum implements BaseEnum {

    /**
     * 用户端首页
     */
    HOMES_HAINAN("海南之家", 1),
    SEARCHED_STORY("搜了故事", 2),
    SEARCHED_HEADLINES("搜了头条", 3);

    private String label;

    private Integer value;

    ArticleTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
