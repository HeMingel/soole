package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum ProductEnum implements BaseEnum {

    /**
     * 用户端首页
     */
    PRODUCT_RECOMMEND_DISABLE("推荐商品-禁用", 0),
    PRODUCT_RECOMMEND_ENABLE("推荐商品-启用", 1);

    private String label;

    private Integer value;

    ProductEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
