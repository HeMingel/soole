package com.songpo.searched.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_position")
public class SlPosition implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "province_id")
    private Long provinceId;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "county_id")
    private Long countyId;
    @Column(name = "county_name")
    private String countyName;
    @Column(name = "town_id")
    private Long townId;
    @Column(name = "town_name")
    private String townName;
    @Column(name = "village_id")
    private Long villageId;
    @Column(name = "village_name")
    private String villageName;

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
     * @return province_id
     */
    public Long getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId
     */
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return province_name
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * @param provinceName
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    /**
     * @return city_id
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * @return city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return county_id
     */
    public Long getCountyId() {
        return countyId;
    }

    /**
     * @param countyId
     */
    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    /**
     * @return county_name
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * @param countyName
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName == null ? null : countyName.trim();
    }

    /**
     * @return town_id
     */
    public Long getTownId() {
        return townId;
    }

    /**
     * @param townId
     */
    public void setTownId(Long townId) {
        this.townId = townId;
    }

    /**
     * @return town_name
     */
    public String getTownName() {
        return townName;
    }

    /**
     * @param townName
     */
    public void setTownName(String townName) {
        this.townName = townName == null ? null : townName.trim();
    }

    /**
     * @return village_id
     */
    public Long getVillageId() {
        return villageId;
    }

    /**
     * @param villageId
     */
    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    /**
     * @return village_name
     */
    public String getVillageName() {
        return villageName;
    }

    /**
     * @param villageName
     */
    public void setVillageName(String villageName) {
        this.villageName = villageName == null ? null : villageName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityId=").append(cityId);
        sb.append(", cityName=").append(cityName);
        sb.append(", countyId=").append(countyId);
        sb.append(", countyName=").append(countyName);
        sb.append(", townId=").append(townId);
        sb.append(", townName=").append(townName);
        sb.append(", villageId=").append(villageId);
        sb.append(", villageName=").append(villageName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}