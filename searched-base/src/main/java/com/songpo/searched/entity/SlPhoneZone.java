package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_phone_zone")
public class SlPhoneZone implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号码地区号
     */
    @Column(name = "mobileArea")
    private Integer mobilearea;

    /**
     * 手机号码归属地
     */
    private String zone;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机号码地区号
     *
     * @return mobileArea - 手机号码地区号
     */
    public Integer getMobilearea() {
        return mobilearea;
    }

    /**
     * 设置手机号码地区号
     *
     * @param mobilearea 手机号码地区号
     */
    public void setMobilearea(Integer mobilearea) {
        this.mobilearea = mobilearea;
    }

    /**
     * 获取手机号码归属地
     *
     * @return zone - 手机号码归属地
     */
    public String getZone() {
        return zone;
    }

    /**
     * 设置手机号码归属地
     *
     * @param zone 手机号码归属地
     */
    public void setZone(String zone) {
        this.zone = zone == null ? null : zone.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobilearea=").append(mobilearea);
        sb.append(", zone=").append(zone);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}