package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_district_area")
public class SlDistrictArea implements Serializable {
    @Id
    private Long code;

    private String name;

    @Column(name = "city_code")
    private Long cityCode;

    @Column(name = "province_code")
    private Long provinceCode;

    private static final long serialVersionUID = 1L;

    /**
     * @return code
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return city_code
     */
    public Long getCityCode() {
        return cityCode;
    }

    /**
     * @param cityCode
     */
    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * @return province_code
     */
    public Long getProvinceCode() {
        return provinceCode;
    }

    /**
     * @param provinceCode
     */
    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}