package com.songpo.searched.typehandler;

/**
 * @author 刘松坡
 */
public enum ActionNavigationTypeEnum implements BaseEnum {

    /**
     * 用户端首页
     */
    CUSTOMER_APP_HOME_BANNER("用户端-首页-轮播图", 1),
    CUSTOMER_APP_HOME_GATEWAY("用户端-首页-入口", 2),
    CUSTOMER_APP_HOME_ACTION("用户端-首页-促销活动", 3),
    CUSTOMER_APP_HOME_VIDEO("用户端-首页-视频", 4),
    CUSTOMER_APP_HOME_SALE_MODE("用户端-首页-销售模式", 5),
    CUSTOMER_APP_HOME_VIDEO_LIVE("用户端-首页-视频直播", 6);

    private String label;

    private Integer value;

    ActionNavigationTypeEnum(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
