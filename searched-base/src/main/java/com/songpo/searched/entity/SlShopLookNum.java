package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_shop_look_num")
public class SlShopLookNum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 访客id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 哪天
     */
    private String day;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取店铺id
     *
     * @return shop_id - 店铺id
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺id
     *
     * @param shopId 店铺id
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * 获取访客id
     *
     * @return user_id - 访客id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置访客id
     *
     * @param userId 访客id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取哪天
     *
     * @return day - 哪天
     */
    public String getDay() {
        return day;
    }

    /**
     * 设置哪天
     *
     * @param day 哪天
     */
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopId=").append(shopId);
        sb.append(", userId=").append(userId);
        sb.append(", day=").append(day);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}