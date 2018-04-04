package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_model_area")
public class SlModelArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 父区域ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 区域名称
     */
    private String name;
    /**
     * 区域短名称
     */
    @Column(name = "short_name")
    private String shortName;
    /**
     * 区域等级
     */
    private Boolean level;
    /**
     * 城市代码
     */
    @Column(name = "city_code")
    private String cityCode;
    /**
     * 邮政编码
     */
    @Column(name = "zip_code")
    private String zipCode;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父区域ID
     *
     * @return parent_id - 父区域ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父区域ID
     *
     * @param parentId 父区域ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取区域名称
     *
     * @return name - 区域名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置区域名称
     *
     * @param name 区域名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取区域短名称
     *
     * @return short_name - 区域短名称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置区域短名称
     *
     * @param shortName 区域短名称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * 获取区域等级
     *
     * @return level - 区域等级
     */
    public Boolean getLevel() {
        return level;
    }

    /**
     * 设置区域等级
     *
     * @param level 区域等级
     */
    public void setLevel(Boolean level) {
        this.level = level;
    }

    /**
     * 获取城市代码
     *
     * @return city_code - 城市代码
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置城市代码
     *
     * @param cityCode 城市代码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * 获取邮政编码
     *
     * @return zip_code - 邮政编码
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮政编码
     *
     * @param zipCode 邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * 获取经度
     *
     * @return lng - 经度
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng 经度
     */
    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    /**
     * 获取纬度
     *
     * @return lat - 纬度
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置纬度
     *
     * @param lat 纬度
     */
    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", shortName=").append(shortName);
        sb.append(", level=").append(level);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", lng=").append(lng);
        sb.append(", lat=").append(lat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}