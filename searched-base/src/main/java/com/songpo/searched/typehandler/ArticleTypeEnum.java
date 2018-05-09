package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum ArticleTypeEnum implements BaseEnum {

    /**
     * 用户端首页
     */
    HOMES_HAINAN("海南之家", 2),
    SEARCHED_STORY("搜了故事", 3),
    SEARCHED_HEADLINES("搜了头条", 1);

    private String label;

    private Integer value;

    ArticleTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
