package com.songpo.searched.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_district_street")
public class SlDistrictStreet implements Serializable {
    @Id
    private Long code;

    private String name;

    @Column(name = "area_code")
    private Long areaCode;

    @Column(name = "province_code")
    private Long provinceCode;

    @Column(name = "city_code")
    private Long cityCode;

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
     * @return area_code
     */
    public Long getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode
     */
    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}