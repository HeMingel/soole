package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_action_navigation_type")
public class SlActionNavigationType implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称，比如：我的、商品、首页
     */
    private String name;

    /**
     * 播放速度，比如轮播图这些可以指定播放速度
     */
    private Float speed;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取名称，比如：我的、商品、首页
     *
     * @return name - 名称，比如：我的、商品、首页
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称，比如：我的、商品、首页
     *
     * @param name 名称，比如：我的、商品、首页
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取播放速度，比如轮播图这些可以指定播放速度
     *
     * @return speed - 播放速度，比如轮播图这些可以指定播放速度
     */
    public Float getSpeed() {
        return speed;
    }

    /**
     * 设置播放速度，比如轮播图这些可以指定播放速度
     *
     * @param speed 播放速度，比如轮播图这些可以指定播放速度
     */
    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", speed=").append(speed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}